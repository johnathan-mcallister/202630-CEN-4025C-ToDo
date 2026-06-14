/**
 * Author: Johnathan McAllister
 * Date: 06-13-26
 * Course: 202630-CEN-4025C
 * Professor: Dr. Mary Walauskis
 *
 * Purpose:
 * -
 *
 * Constraints:
 * -
 */

import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

public class ToDoApp {

    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int choice;

        tasks.add(new Task(
                "Complete Java Assignment",
                LocalDateTime.of(2026, 6, 20, 23, 59),
                ""
        ));

        tasks.add(new Task(
                "Study for Midterm",
                LocalDateTime.of(2026, 6, 18, 18, 00),
                ""
        ));

        tasks.add(new Task(
                "Submit Project Proposal",
                LocalDateTime.of(2026, 6, 16, 17, 00),
                ""
        ));

        tasks.add(new Task(
                "Attend Team Meeting",
                LocalDateTime.of(2026, 6, 17, 9, 30),
                ""
        ));

        tasks.add(new Task(
                "Pay Internet Bill",
                LocalDateTime.of(2026, 6, 15, 12, 00),
                ""
        ));

        tasks.add(new Task(
                "Buy Groceries",
                LocalDateTime.of(2026, 6, 14, 16, 00),
                ""
        ));

        tasks.add(new Task(
                "Schedule Dentist Appointment",
                LocalDateTime.of(2026, 6, 25, 10, 00),
                ""
        ));

        tasks.add(new Task(
                "Review Resume",
                LocalDateTime.of(2026, 6, 19, 20, 00),
                ""
        ));

        tasks.add(new Task(
                "Backup Laptop",
                LocalDateTime.of(2026, 6, 21, 22, 00),
                ""
        ));


        do {
            showMenu();
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addTask();
                case 2 -> editTask();
                case 3 -> deleteTask();
                case 4 -> listTasks();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    public static void showMenu() {
        System.out.println("\n==== TODO APP ====");
        System.out.println("1. Add Task");
        System.out.println("2. Edit Task");
        System.out.println("3. Delete Task");
        System.out.println("4. List Tasks");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    public static void addTask() {
        boolean valid = false;
        LocalDateTime dueDate = null;
        
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        while (!valid) {
            try {
                System.out.print("Enter due date (examples: 2026-06-13, 06/13/2026, 2026-06-13 14:30, 06/13/2026 2:30 PM): ");

                dueDate = parseFlexibleDateTime(scanner.nextLine().trim());

                valid = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        tasks.add(new Task(title, dueDate, description));

        System.out.println("Task added.");
    }

    public static void editTask() throws Exception {
        boolean valid = false;

        listTasks();

        System.out.print("Enter task number to edit: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        Task task = tasks.get(index);

        System.out.print("Enter new title: ");
        task.setName(scanner.nextLine());

        while (!valid) {
            try {
                System.out.print("Enter due date (examples: 2026-06-13, 06/13/2026, 2026-06-13 14:30, 06/13/2026 2:30 PM): ");

                LocalDateTime dueDate = parseFlexibleDateTime(scanner.nextLine().trim());

                task.setDueDate(dueDate);
                valid = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter new description: ");
        task.setDescription(scanner.nextLine());

        System.out.println("Task updated.");
    }

    public static void deleteTask() {
        listTasks();

        System.out.print("Enter task number to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        tasks.remove(index);

        System.out.println("Task deleted.");
    }

    public static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        tasks.sort(Comparator.comparing(Task::getDueDate));

        System.out.println("\n==== TASKS ====");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public static LocalDateTime parseFlexibleDateTime(String input) {

        List<DateTimeFormatter> dateTimeFormats = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"),
                DateTimeFormatter.ofPattern("M/d/yyyy h:mm a"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        );

        List<DateTimeFormatter> dateFormats = List.of(
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
                DateTimeFormatter.ofPattern("MMMM d yyyy")
        );

        // Try DateTime formats first
        for (DateTimeFormatter formatter : dateTimeFormats) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Then try Date-only formats
        for (DateTimeFormatter formatter : dateFormats) {
            try {
                return LocalDate.parse(input, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException(
            "Invalid date format. Examples:\n" +
                    "2026-06-13\n" +
                    "2026-06-13 14:30\n" +
                    "06/13/2026\n" +
                    "06/13/2026 2:30 PM"
        );
    }
}
