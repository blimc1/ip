# Doge User Guide

Doge is a Java-based CLI task manager chatbot to help users keep track of various types of tasks.

## Quick Start
This chatbot runs on Java 17, so please ensure that you have it installed on your computer.

Download the latest .jar file.

Open a terminal and cd into the folder that you saved the .jar file in. 

Run java -jar Doge.jar.


## Features
### Adding Tasks

---
#### ToDo - Adds a basic task without dates or times

Input: `todo <task>`

Note: `<task>` cannot be empty

Expected example input and output:
```
todo read book
____________________________________________________________
Got it. I've added this task:
[T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
```
---
#### Deadline - Adds a task with a due date/time

Input: `deadline <task> /by <time>`

Note: `<task>` and `<time>` cannot be empty

Expected example input and output:

```
deadline finish reading book /by tomorrow
____________________________________________________________
Got it. I've added this task:
[D][ ] finish reading book (by: tomorrow)
Now you have 2 tasks in the list.
____________________________________________________________

```
---
#### Event - Adds a task with a start and end time

Input: `event <task> /from <time> /to <time>`

Note: `<task>` and `<time>`s cannot be empty

Expected example input and output:

```
event continue reading book /from now /to 2pm
____________________________________________________________
Got it. I've added this task:
[E][ ] continue reading book (from: now to: 2pm)
Now you have 3 tasks in the list.
____________________________________________________________

```
---
#### List - Lists all tasks

Input: `list`

Expected input and output:

```
list
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] finish reading book (by: tomorrow)
3.[E][ ] continue reading book (from: now to: 2pm)
____________________________________________________________
```
---
#### Mark - Marks a task as done

Input: `mark <task number>`

Note: `<task number>` cannot be empty

Expected example input and output:

```
mark 3
____________________________________________________________
Nice! I've marked this task as done:
[E][X] continue reading book (from: now to: 2pm)
____________________________________________________________
```
---
#### Unmark - Unmarks a task previously marked as done

Input: `unmark <task number>`

Note: `<task number>` cannot be empty

Expected example input and output:

```
unmark 3
____________________________________________________________
OK, I've marked this task as not done yet:
[E][ ] continue reading book (from: now to: 2pm)
____________________________________________________________

```
---
#### Delete - Deletes a task from the list

Input: `delete <task number>`

Note: `<task number>` cannot be empty

Expected example input and output:

```
delete 1
____________________________________________________________
Aight. Task deletus:
[T][ ] read book
Now you have 2 tasks in the list.
____________________________________________________________
```
---
#### Find - Find a task based on a specific keyword

Input: `find <keyword(s)>`

Note:
`<keyword(s)>` cannot be empty, and must be an exact match of what you want to find

Expected example input and output:

```
find reading book
____________________________________________________________
Nice! I've marked this task as done:
[E][X] continue reading book (from: now to: 2pm)
____________________________________________________________
```
---
#### Bye - Exit the chatbot

Input: `bye`

Expected example input and output:

```
bye
____________________________________________________________
 Bye. Hope to see you again soon!
____________________________________________________________
```
---
## Saving and loading tasks
* The chatbot automatically saves whatever tasks you have inputted
* You will be able to recall the saved tasks after re-opening the chatbot