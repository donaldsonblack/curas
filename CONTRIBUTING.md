# Contributing to Cura-server

This document outlines our conventions and workflow so contributions stay consistent and easy to review.

---

## Branch Naming

Branches should follow the pattern:

```
<type>/<short-description>
```

**Types:**
- `feat` – New feature or functionality  
- `fix` – Bug fixes or patches  
- `chore` – Maintenance tasks (deps, configs, CI)  
- `docs` – Documentation only  
- `refactor` – Code changes that neither fix a bug nor add a feature  
- `test` – Adding or modifying tests  

**Examples:**
- `feat/user-authentication`  
- `fix/department-endpoint-pagination`  
- `docs/contributing-guide`  

---

## Commit Messages

We follow a **conventional commits** style:

```
<type>(scope?): <short summary>
```

- Use the imperative mood (e.g. “add”, not “added” or “adds”).  
- Keep the summary under **72 characters**.  
- The scope is optional, but helpful when targeting a specific module.  

**Examples:**
- `feat(auth): add JWT refresh token handling`  
- `fix(api): correct null pointer in department lookup`  
- `chore(deps): update Spring Boot to 3.2.1`  

---

## Pull Requests

- Keep PRs **focused** (one feature or fix per PR).  
- Reference related issues in the description (e.g. `Closes #42`).  
- Make sure code is formatted consistently with the project style.  
