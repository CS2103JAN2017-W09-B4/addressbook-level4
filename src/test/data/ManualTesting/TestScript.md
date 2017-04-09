# A. Steps to load the sample data
1. Navigate to the doTASK folder

# B. Testing

### Pointers to take note for expected output
> *current date* and *current time* refers to the current date/time by the user’s local machine.

### Add Command: `add`
1. **Command**: `add Kayaking at Kallang d/tomorrow at 2pm`<br>
**Expected**: New Task added: Kayaking at Kallang Deadline: *current date* @ 14:00 Priority Level:  Information:  Tags:

2. **Command**: `add Walk the dog d/today 6pm to 8pm p/4`<br>
**Expected**: New Task added: Walk the dog Deadline: *current date* @ 18:00 to *current date* @ 20:00 Priority Level: 4 Information:  Tags:

3. **Command**: `add Meet John d/this sat @ 10am p/2 i/ION for shopping`<br>
**Expected**: New Task added: Meet John Deadline: 15-Apr-2017 @ 10:00 Priority Level: 2 Information: ION for shopping Tags:

4. **Command**: `add Dental Appointment d/26 May 2017 @ 4pm p/1 i/KTPH t/Appointment`<br>
**Expected**: New Task added: Dental Appointment Deadline: 26-May-2017 @ 16:00 Priority Level: 1 Information: KTPH Tags: [Appointment]

5. **Command**: `add Tidy up the house d/today to tomorrow t/Chore`<br>
**Expected**: New Task added: Tidy up the house Deadline: 09-Apr-2017 @ *current time* to 10-Apr-2017 @ *current time* Priority Level:  Information:  Tags: [Chore]

6. **Command**: `add Remind mum about spoilt kettle t/Home`<br>
**Expected**: New Task added: Remind mum about spoilt kettle Deadline:  Priority Level:  Information:  Tags: [Home]

7. **Command**: `add Overdue task stub d/last monday i/showing the feature of overdue t/Overdue`<br>
**Expected**: New Task added: Overdue task stub Deadline: 03-Apr-2017 @ *current time* Priority Level:  Information: showing the feature of overdue Tags: [Overdue]

8. **Command**: `add Overdue event stub d/10 jan to 10 feb i/Overdue event t/Overdue`<br>
**Expected**: New Task added: Overdue event stub Deadline: 10-Jan-2017 @ 18:17 to 10-Feb-2017 @ 18:17 Priority Level:  Information: Overdue event Tags: [Overdue]


### Edit Command: `edit`
9. **Command** : `edit 3 d/10 march @ 2300hrs`<br>
**Expected**: Edited Task: Go out for a drink with friends Deadline: 10-Mar-2017 @ 23:00 Priority Level: 3 Information: Clarke Quay Tags: [Personal]

10. **Command**: `edit 4 d/30 June @ 10pm`<br>
**Expected**: Edited Task: Go out for a drink with friends Deadline: 30-Jun-2017 @ 22:00 Priority Level: 3 Information: Clarke Quay Tags: [Personal]

11. **Command**: `edit 52 i/When mum comes back home`<br>
**Expected**: Edited Task: Remind mum about spoilt kettle Deadline:  Priority Level:  Information: When mum comes back home Tags: [Home]

12. **Command**: `edit 52 p/1`<br>
**Expected**: Edited Task: Remind mum about spoilt kettle Deadline:  Priority Level: 1 Information: When mum comes back home Tags: [Home]

13. **Command**: `edit 48 t/Hobby t/New`<br>
**Expected**: Edited Task: Learn kayaking over the hols Deadline:  Priority Level:  Information:  Tags: [New][Hobby]

14. **Command**: `edit 6 Walk the cat i/Dog too mainstream`<br>
**Expected**: Edited Task: Walk the cat Deadline: 09-Apr-2017 @ 18:00 to 09-Apr-2017 @ 20:00 Priority Level: 4 Information: Dog too mainstream Tags:

### Delete Command: `delete`
15. **Command**: `delete 1`<br>
**Expected**: Deleted Task: Celebrate Christmas Deadline: 25-Dec-2016 @ 23:59 Priority Level: 4 Information:  Tags: [Home]

### Undo Command: `undo` & Redo Command: `redo`

##### _undo-delete_
16. **Command**: `undo`<br>
**Expected**: Last action undone

##### _redo-delete_
17. **Command**: `redo`<br>
**Expected**: Last action reverted

##### _undo-add_
18. **Command**: `add todo d/24 dec 2016`<br>
**Expected**: New Task added: todo Deadline: 24-Dec-2016 @ 00:05 Priority Level:  Information:  Tags:<br>
**Command**: `undo`<br>
**Expected**: Last action undone

##### _redo-add_
19. **Command**: `redo`<br>
**Expected**: Last action reverted

##### _undo-edit_
20. **Command**: `edit 1 i/information p/4`<br>
**Expected**: Edited Task: todo Deadline: 24-Dec-2016 @ 00:05 Priority Level: 4 Information: information Tags:<br>
**Command**: `undo`<br>
**Expected**: Last action undone

##### _redo-edit_
21. **Command**: `redo`<br>
**Expected**: Last action reverted

##### _undo-clear_
22. **Command**: `clear`<br>
**Expected**: doTASK has been cleared!<br>
**Command**: `undo`<br>
**Expected**: Last action undone


### Find Command: `find`
23. **Command**: `find taeyeon`<br>
**Expected**: 1 Task(s) listed! Including 1 Exact Match case(s) & 0 Near Match case(s).

24. **Command**: `find overdue`<br>
**Expected**: 2 Task(s) listed! Including 2 Exact Match case(s) & 0 Near Match case(s).

25. **Command**: `find over`<br>
**Expected**: 4 Task(s) listed! Including 1 Exact Match case(s) & 3 Near Match case(s).

26. **Command**: `find a`<br>
**Expected**: 43 Task(s) listed! Including 5 Exact Match case(s) & 38 Near Match case(s).

27. **Command**: `find dwayne`<br>
**Expected**: 0 Task(s) listed! Including 0 Exact Match case(s) & 0 Near Match case(s).


### List Command: `list`
28. **Command**: `list`<br>
**Expected**: Listed all tasks!


### Complete Command: `complete`

29. **Command**: “complete 6”<br>
**Expected**: Task completed: Walk the cat Deadline: 09-Apr-2017 @ 18:00 to 09-Apr-2017 @ 20:00 Priority Level: 4 Information: Dog too mainstream Tags:

### Uncomplete Command: `uncomplete`
30. **Command**: “uncomplete 6”<br>
**Expected**: Task uncompleted: Walk the cat Deadline: 09-Apr-2017 @ 18:00 to 09-Apr-2017 @ 20:00 Priority Level: 4 Information: Dog too mainstream Tags:

### Deletecompleted Command: `deletecompleted`
31. **Command**: “deletecompleted 4”<br>
**Expected**: Completed Task deleted: Do up presentation for GET1006 Deadline: 30-Mar-2017 @ 23:59 Priority Level: 1 Information:  Tags: [School]
