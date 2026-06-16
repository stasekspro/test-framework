---
description: Write automated UI tests for a given feature using the test-developer skill
allowed-tools: Read, Edit, Write, Bash, Browser
---

You are given the following task to implement automated UI tests for:

$ARGUMENTS

Use the `test-developer` skill to complete this task end-to-end:
1. Explore the codebase and understand what already exists
2. Open onliner.by in the browser and inspect the relevant UI elements
3. Write Page Object changes if needed
4. Write the test(s)
5. Run `mvn test` and make sure everything is GREEN
6. Invoke `code-reviewer` as a subagent to review the result
7. Apply all review fixes
8. Run tests one final time to confirm GREEN

Do not stop until `mvn test` shows BUILD SUCCESS.
