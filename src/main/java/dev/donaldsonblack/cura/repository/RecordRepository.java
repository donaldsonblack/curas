package dev.donaldsonblack.cura.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.donaldsonblack.cura.model.records.Record;
import dev.donaldsonblack.cura.model.records.RecordDetail;
import dev.donaldsonblack.cura.model.records.RecordPatchRequest;
import dev.donaldsonblack.cura.model.records.RecordCreateRequest;
import org.postgresql.util.PGobject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordRepository {

    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;

    public RecordRepository(JdbcTemplate jdbc, ObjectMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    // =======================
    // CRUD for base Record
    // =======================

    /** Insert using RecordCreateRequest; returns the created Record. */
    public Record create(RecordCreateRequest req) {
        final String sql = """
            INSERT INTO record (checklist_id, author_id, answers)
            VALUES (?, ?, ?::jsonb)
            RETURNING *
            """;
        return jdbc.queryForObject(
            sql,
            recordRowMapper(),
            req.getChecklistId(),
            req.getAuthorId(),
            (req.getAnswers() == null) ? "null" : req.getAnswers().toString()
        );
    }

    /** Save/Upsert a full Record (when you already have the fields). */
    public Record upsert(Record rec) {
        if (existsById(rec.getId())) {
            final String sql = """
                UPDATE record
                SET checklist_id = ?, author_id = ?, answers = ?::jsonb
                WHERE id = ?
                RETURNING id, checklist_id, author_id, created, answers
                """;
            return jdbc.queryForObject(
                sql,
                recordRowMapper(),
                rec.getChecklistId(),
                rec.getAuthorId(),
                (rec.getAnswers() == null) ? "null" : rec.getAnswers().toString(),
                rec.getId()
            );
        } else {
            final String sql = """
                INSERT INTO record (id, checklist_id, author_id, answers)
                VALUES (?, ?, ?, ?::jsonb)
                RETURNING id, checklist_id, author_id, created, answers
                """;
            return jdbc.queryForObject(
                sql,
                recordRowMapper(),
                rec.getId(),
                rec.getChecklistId(),
                rec.getAuthorId(),
                (rec.getAnswers() == null) ? "null" : rec.getAnswers().toString()
            );
        }
    }

    /** Patch selectively; returns updated Record. */
    public Optional<Record> patch(int id, RecordPatchRequest req) {
        // Fetch current
        Optional<Record> existing = findRecordById(id);
        if (existing.isEmpty()) return Optional.empty();

        Record curr = existing.get();
        int checklistId = (req.getChecklistId() != null) ? req.getChecklistId() : curr.getChecklistId();
        int authorId    = (req.getAuthorId()    != null) ? req.getAuthorId()    : curr.getAuthorId();
        JsonNode answers= (req.getAnswers()     != null) ? req.getAnswers()     : curr.getAnswers();

        final String sql = """
            UPDATE record
            SET checklist_id = ?, author_id = ?, answers = ?::jsonb
            WHERE id = ?
            RETURNING id, checklist_id, author_id, created, answers
            """;
        Record updated = jdbc.queryForObject(
            sql,
            recordRowMapper(),
            checklistId,
            authorId,
            (answers == null) ? "null" : answers.toString(),
            id
        );
        return Optional.ofNullable(updated);
    }

    public Optional<Record> findRecordById(int id) {
        try {
            final String sql = """
                SELECT id, checklist_id, author_id, created, answers
                FROM record WHERE id = ?
                """;
            return Optional.ofNullable(jdbc.queryForObject(sql, recordRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Record> findRecordsByChecklistId(int checklistId) {
        final String sql = """
            SELECT id, checklist_id, author_id, created, answers
            FROM record
            WHERE checklist_id = ?
            ORDER BY created DESC, id DESC
            """;
        return jdbc.query(sql, recordRowMapper(), checklistId);
    }

    public List<Record> findRecordsByAuthorId(int authorId) {
        final String sql = """
            SELECT id, checklist_id, author_id, created, answers
            FROM record
            WHERE author_id = ?
            ORDER BY created DESC, id DESC
            """;
        return jdbc.query(sql, recordRowMapper(), authorId);
    }

    public List<Record> findAllRecords() {
        final String sql = """
            SELECT id, checklist_id, author_id, created, answers
            FROM record
            ORDER BY created DESC, id DESC
            """;
        return jdbc.query(sql, recordRowMapper());
    }

    public boolean deleteById(int id) {
        return jdbc.update("DELETE FROM record WHERE id = ?", id) > 0;
    }

    public boolean existsById(int id) {
        Integer cnt = jdbc.queryForObject("SELECT 1 FROM record WHERE id = ?", Integer.class, id);
        return cnt != null;
    }

    // =======================
    // Joined "Detail" queries
    // =======================

    public Optional<RecordDetail> findDetailById(int id) {
        try {
            String sql = BASE_DETAIL + " WHERE r.id = ?";
            return Optional.ofNullable(jdbc.queryForObject(sql, detailRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<RecordDetail> findDetailsByChecklistId(int checklistId) {
        String sql = BASE_DETAIL + " WHERE r.checklist_id = ? ORDER BY r.created DESC, r.id DESC";
        return jdbc.query(sql, detailRowMapper(), checklistId);
    }

    public List<RecordDetail> findDetailsByAuthorId(int authorId) {
        String sql = BASE_DETAIL + " WHERE r.author_id = ? ORDER BY r.created DESC, r.id DESC";
        return jdbc.query(sql, detailRowMapper(), authorId);
    }

    public List<RecordDetail> findAllDetails() {
        String sql = BASE_DETAIL + " ORDER BY r.created DESC, r.id DESC";
        return jdbc.query(sql, detailRowMapper());
    }

    // =======================
    // SQL + Mappers
    // =======================

    private static final String BASE_DETAIL = """
        SELECT
            r.id,
            r.checklist_id,
            r.author_id,
            r.created,
            r.answers,
            u.name  AS author_name,
            e.name  AS equipment_name,
            e.model AS equipment_model,
            e.serial AS equipment_serial,
            d.name  AS department_name
        FROM record r
        LEFT JOIN users      u ON u.id = r.author_id
        LEFT JOIN checklists c ON c.id = r.checklist_id
        LEFT JOIN equipment  e ON e.id = c.equipment_id
        LEFT JOIN department d ON d.id = c.department_id
        """;

    private RowMapper<Record> recordRowMapper() {
        return (rs, rowNum) -> Record.builder()
            .id(rs.getInt("id"))
            .checklistId(getIntOrZero(rs, "checklist_id"))
            .authorId(getIntOrZero(rs, "author_id"))
            .created(toInstant(rs, "created"))
            .answers(toJsonNode(rs, "answers"))
            .build();
    }

    private RowMapper<RecordDetail> detailRowMapper() {
        return (rs, rowNum) -> RecordDetail.builder()
            .id(rs.getInt("id"))
            .checklistId(getIntOrZero(rs, "checklist_id"))
            .authorId(getIntOrZero(rs, "author_id"))
            .created(toInstant(rs, "created"))
            .answers(toJsonNode(rs, "answers"))
            .authorName(rs.getString("author_name"))
            .equipmentName(rs.getString("equipment_name"))
            .equipmentModel(rs.getString("equipment_model"))
            .equipmentSerial(rs.getString("equipment_serial"))
            .departmentName(rs.getString("department_name"))
            .build();
    }

    // =======================
    // Helpers
    // =======================

    private int getIntOrZero(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? 0 : v;
    }

    private Instant toInstant(ResultSet rs, String col) throws SQLException {
        var ts = rs.getTimestamp(col);
        return ts == null ? null : ts.toInstant();
    }

    private JsonNode toJsonNode(ResultSet rs, String col) throws SQLException {
        Object obj = rs.getObject(col);
        try {
            if (obj == null) return null;
            if (obj instanceof PGobject pg) {
                return mapper.readTree(pg.getValue());
            }
            return mapper.readTree(String.valueOf(obj));
        } catch (Exception e) {
            throw new SQLException("Failed to parse JSON for column '" + col + "'", e);
        }
    }
}
