# Day 8

Part 1: Instead of checking the up, down, left, and right of every tree individually. I checked along each row/col in both directions, keeping track of the tallest tree. This can then be used to determine if a tree is visible. I then cross-checked this with current not visible trees.

Part 2: This did require checking all directions for each tree. Keeping track of the current highest scenic score.

Takes around 22ms
