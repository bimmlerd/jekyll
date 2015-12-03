# Stackframe Specification of Team jekyll

We adhere to the C calling conventions:

Arguments above %ebp, locals below %ebp.

Stack diagram of Main$main and a called function
```
 --------------------------------  Main$main
| old %ebp                       |
| local 1                        |
| local 2                        |
| ...                            |
| local m                        |
| temp 1                         |
| ....                           |
| temp k - 1                     |
| temp k                         |
|--------------------------------|
| Stack alignment padding        | to ensure 16-byte alignment
|--------------------------------|
| argument n                     |
| argument n - 1                 |
| ...                            |
| argument 2                     |
| argument 1 (target)            |
| return address                 | caller
|--------------------------------| stack frame border, beginning of user called function's stack frame
| old %ebp                       | <- %ebp
| local 1                        |
| local 2                        |
| ...                            |
| local m                        |
| temp 1                         |
| ....                           |
| temp k - 1                     |
| temp k                         | <- %esp
 --------------------------------
```

Note that in every method call, the target object is the first argument.