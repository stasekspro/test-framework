---
description: Write a stable, reviewed UI test for the given feature. Uses test-developer skill end-to-end.
argument-hint: <task description>
---

Use the test-developer skill to implement the following task:

$ARGUMENTS

Do not stop until:
1. `mvn test -Dtest=OnlinerTests#newTestMethod` shows BUILD SUCCESS with the new test included
2. code-reviewer has reviewed the code once, and every issue it raised has been fixed
3. Tests are still green after applying the fixes