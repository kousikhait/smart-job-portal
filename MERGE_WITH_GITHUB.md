# Merge current project with your existing GitHub repo

Run these in **Git Bash** or a terminal where `git` works. Go to the project folder first:

```bash
cd D:\kousik\jobportal\jobportal
```

---

## Option A: Merge (keep old + new history)

Use this if you want to keep the old commits on GitHub and add your new code as new commits.

1. **Initialize and make first commit (if not already a git repo):**
   ```bash
   git init
   git add .
   git commit -m "Latest version: external jobs, apply URL, sample data, fixes"
   ```

2. **Add your existing GitHub repo as remote** (replace with your actual repo URL):
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   ```

3. **Fetch and merge the old version from GitHub** (creates one merge commit):
   ```bash
   git fetch origin
   git branch -M main
   git pull origin main --allow-unrelated-histories
   ```
   If Git says "fatal: refusing to merge unrelated histories", you already have no common history; `--allow-unrelated-histories` is needed. If there are **merge conflicts**, Git will list the files. Open those files, fix the conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`), then:
   ```bash
   git add .
   git commit -m "Merge with existing repo"
   ```

4. **Push the merged result:**
   ```bash
   git push -u origin main
   ```

---

## Option B: Replace (current code overwrites GitHub)

Use this if you don't care about keeping the old commits and want the repo to look exactly like your current folder.

1. **Initialize and commit (if not already done):**
   ```bash
   git init
   git add .
   git commit -m "Latest version: external jobs, apply URL, sample data, fixes"
   ```

2. **Add your existing GitHub repo:**
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   ```

3. **Overwrite the branch on GitHub** (this removes old history on `main`):
   ```bash
   git branch -M main
   git push -u origin main --force
   ```

---

Replace `YOUR_USERNAME` and `YOUR_REPO_NAME` with your real GitHub username and repository name.
