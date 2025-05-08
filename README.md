# java-kanban
## Done
### AbstractTask
AbstractTask has four variables: integer id, string name, string description and status; 
one constructor with parameters; else getters and setters.

The method setStatus is abstract.

### Satus
Enum Status has three parameters: NEW, IN_PROGRESS, DONE.

### Task
Class Task, extends the AbstractTask, has two constructors: first with parameters and second with other task
for copy, and override methods: hashCode, equals and toString for testing.

The method setStatus also override, it takes status and uses to itself status.

### EpicTask
Class EpicTask, extends the AbstractTask, has two constructors: first with parameters and second with other epictask
for copy, and override methods: hashCode, equals and toString for testing.

The method setStatus also override, it takes status and uses to itself status, if status is NEW or DONE, current 
tasks' statuses in the epictask change on the took status.

Added method addTask, it creates new task in the epictask; method getTaskById, it searches task in the epic by id,
if id doesn't exist throw exception; method getAllTasks, returns all tasks of the epic; method getCountTasks, 
gets count elements in the epic; method removeTask, removes task in the epic by id, if id doesn't exist throw exception; 
private method checkStatus, checks status for each tasks in the epic.

### Manager
Class manager has one constructor, a map tasks and a counter.

Added method getNewId, it gets id for tasks and epics; getAll, it returns map with all tasks and epics; removeAll, 
remove all tasks and reset counter; getById, searches a task or an epic by id, searches even in epics, 
if id doesn't exist throw exception; createTask and createEpicTask; updateTask, updates tasks data by id, updates even
tasks in any epic; removeById, removes a task or an epic by id, removes even tasks in any epic; getTasksOfEpic, gets map 
of tasks in the epic, if the epic with id doesn't exist, throws exception.

### TestManager
Class TestManager, is located in the folder tests, checks class Manager.

### Note
Have been two classes: EpicTask and Task, because functions Task and Subtask are similar.
Class EpicTask allows to create not only Tasks, but other EpicTasks, it's need for next extend application with a few
deep levels. Here use for one HashMap, because tasks and epics extends AbstractTask, then use HashMap with abstract class
and, as needed, abstract class converts to need class(task or epic);