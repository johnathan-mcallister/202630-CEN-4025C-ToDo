/**
 * Author: Johnathan McAllister (McAdmin)
 * Date: 2026-06-13
 * Course:
 * Professor:
 * <p>
 * Purpose:
 * -
 * <p>
 * Constraints:
 * -
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    String name;
    String description;
    LocalDateTime dueDate;

    public Task(String Name, LocalDateTime dueDate, String description) {
        this.name = Name;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

        return "{"
                + " \"name\":\"" + name + "\","
                + " \"description\":\"" + description + "\","
                + " \"dueDate\":\"" + dueDate.format(df) + "\""
                + "}";
    }
}
