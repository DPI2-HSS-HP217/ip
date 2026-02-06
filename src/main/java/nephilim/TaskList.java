package nephilim;
import java.util.ArrayList;

/**
 * TaskList class contains the list of tasks available to user. Handles operations
 * related to changing the list contents (deleting, adding, marking, etc.)
 */

class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask(int index) throws NephilimIOMissingArgsException {
            try {
                return tasks.get(index);
            } catch (IndexOutOfBoundsException e) {
            throw new NephilimIOMissingArgsException("delete " + (index + 1), "Attempting to "
                    + "access a task that does not exist.");
        }
    }

    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Attempts to delete the task at the given index.
     *
     * @param index Index of task to be deleted in 0-based index.
     * @throws NephilimIOMissingArgsException If index of task to be deleted is out of bounds.
     */
    public void deleteTask(int index) throws NephilimIOMissingArgsException {
        try {
            this.tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new NephilimIOMissingArgsException("delete " + (index + 1), "Attempting to "
                    + "access a task that does not exist.");
        }

    }

    /** Attempts to mark the task at the given index as completed. No real effect
     *  if task was already marked.
     *
     * @param index Index of task to be marked in 0-based index.
     * @throws NephilimIOMissingArgsException If index of task to be marked is out of bounds.
     */
    public void markTask(int index)  throws NephilimIOMissingArgsException {
        try {
            this.tasks.get(index).setDone();
        } catch (IndexOutOfBoundsException e) {
            throw new NephilimIOMissingArgsException("mark " + (index + 1), "Attempting to "
                    + "access a task that does not exist.");
        }
    }

    /** Attempts to mark the task at the given index as uncompleted. No real effect
     *  if task was already marked.
     *
     * @param index Index of task to be unmarked in 0-based index.
     * @throws NephilimIOMissingArgsException If index of task to be unmarked is OOB.
     */
    public void unmarkTask(int index)  throws NephilimIOMissingArgsException {
        try {
            this.tasks.get(index).setUnDone();
        } catch (IndexOutOfBoundsException e) {
            throw new NephilimIOMissingArgsException("unmark " + (index + 1), "Attempting to "
                    + "access a task that does not exist.");
        }
    }

    /**
     * Attempts to add a task to the task list.
     * @param T Task to be added
     */
    public void addTask(Task T) {
        this.tasks.add(T);
    }



    /**
     * Lists out all the tasks with a line break between each one.
     * @return A string representing all tasks.
     */
    public String listOut() throws NephilimIOMissingArgsException {
        StringBuilder outputMessage = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            outputMessage.append((i + 1) + ". " + this.getTask(i));
            outputMessage.append('\n');
        }
        return outputMessage.toString();
    }

    /**
     * Lists out all the tasks that contain the given substring in their task name
     * with a line break between each one.
     * @param filter The substring to look for.
     * @return A string representing all tasks with task name containing the substring.
     */
    public String listOut(String filter) throws NephilimIOMissingArgsException {
        StringBuilder outputMessage = new StringBuilder();
        int taskCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskName().contains(filter)) {
                taskCount++;
                outputMessage.append(taskCount + ". " + this.getTask(i));
                outputMessage.append('\n');
            }
        }
        return outputMessage.toString();
    }

}
