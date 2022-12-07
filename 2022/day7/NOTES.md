# Day 7

Many ways of doing the file system. I ended up just using a HashMap that stores every path and its size.
Then keeping tracking of the current path.

Part 2 was to just sort them in descending order, then find the first directory that does NOT free up
enough space. That tells you the previous one is the smallest directory that frees up enough space.

Takes around 18ms
