# Developer Guide

## Table of Contents

- [Acknowledgements](#acknowledgements)
- [Design and Implementation](#design-and-implementation)
    - [Limit Component](#limit-component)
    - [Add Expense Feature](#add-expense-feature)
    - [View Expense Feature](#view-expense-feature)
    - [Delete Expense Feature](#delete-expense-feature)
    - [Currency Exchange Feature](#currency-exchange-feature)
- [Product Scope](#product-scope)
    - [Target User Profile](#target-user-profile)
    - [Value Proposition](#value-proposition)
- [User Stories](#user-stories)
- [Non-Functional Requirements](#non-functional-requirements)
- [Glossary](#glossary)
- [Instructions for Manual Testing](#instructions-for-manual-testing)

---

## Acknowledgements

*List sources of all reused/adapted ideas, code, documentation, and third-party libraries. Include links to the original source.*

---

## Design and Implementation

### Architecture 

![Architecture](UML_diagrams/images/MainArchitecture.png)

#### Main components of the architecture 

| Component          | Responsibility                                                                          | 
|--------------------|-----------------------------------------------------------------------------------------|
| **Ui**             | Handles user input and displays app output                                              | 
| **Finbro**         | Initializes other components in the correct sequence, and connects them with each other |
| **Parser**         | Parses the user's input and initializes the corresponding command object                | 
| **Command**        | Executes user's command                                                                 | 
| **Finances**       | Consists of expenses and the spending limit. Contains the data that the user inputs     | 
| **Storage**        | Reads data from, and writes data to the hard disk                                       | 
| **Budget Warning** | Warns user if expenses is approaching/exceeded the spending limit                       | 

---

### Limit Component

#### Overview

| Component                   | Responsibility                                                                                                                                                                                             |
|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`Limit.java`**            | Stores the limit as a static variable accessible across the application. Using a static variable prevents inconsistent limit values and eliminates the need to pass a `Limit` object across class methods. |
| **`SetLimitCommand.java`**  | Handles validation and user confirmation logic, improving separation of concerns.                                                                                                                          |
| **`EditLimitCommand.java`** | Handles the interactive process of modifying an existing monthly spending limit.                                                                                                                           |

---

#### Setting the Limit

The sequence diagram below illustrates the interaction within the `Limit` component when a user inputs `limit 100`.

![Set Limit Sequence Diagram](UML_diagrams/images/SetLimit.png)

**Flow:**

| Step | Action                                                                                                                                                                                                                          |
|------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1    | **User Input** — The `Ui` receives the input and passes it to `Finbro`                                                                                                                                                          |
| 2    | **Parsing** — `Finbro` passes the input to `Parser`, which creates a `SetLimitCommand` object                                                                                                                                   |
| 3    | **Validation & Confirmation** — The command object verifies the user's input limit: If valid, the system requests confirmation from the user. If the user inputs `"yes"`, the limit is updated; otherwise, it remains unchanged |
| `    | **Display** — `Ui` shows the updated limit                                                                                                                                                                                      |
| 5    | **Persistence** — `Finbro` updates the limit in the `Storage` file                                                                                                                                                              |

---

#### Editing the Limit

The sequence diagram below illustrates the interaction within the `Limit` component when a user inputs `edit limit`.

![Edit Limit Sequence Diagram](UML_diagrams/images/EditLimit.png)

**Flow:**

| Step | Action                                                                                                                                                                                 |
|------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1    | **User Input** — The `Ui` receives the input and passes it to `Finbro`                                                                                                                 |
| 2    | **Parsing** — `Finbro` passes the input to `Parser`, which creates an `EditLimitCommand` object                                                                                        |
| 3    | **Retrieve Current Limit** — The command object retrieves the current limit from `Limit`                                                                                               |
| `    | **Edit Menu** — `Ui` displays an edit menu with three options: Increase, Decrease, or Replace the limit                                                                                |
| 5    | **Amount Entry** — The user enters the corresponding amount                                                                                                                            |
| 6    | **Validation** — `EditLimitCommand` validates: Must be a valid number, not negative, and if decreasing, must not result in below `$0`                                                  |
| 7    | **Confirmation** — If valid, `EditLimitCommand` computes the new limit and calls confirmation logic. If the user inputs `"yes"`, the limit is updated; otherwise, it remains unchanged |
| 8    | **Display** — `Ui` shows the updated limit                                                                                                                                             |
| 9    | **Persistence** — `Finbro` updates the limit in the `Storage` file                                                                                                                     |

---

### Add Expense Feature

The `add` command records a new expense in the system. It supports two modes of operation:

1. **Direct Mode** — All required parameters are provided in a single command
2. **Walkthrough Mode** — The system interactively prompts the user for input

This dual behavior improves usability by supporting both experienced users (fast entry) and new users (guided input).

---

#### Command Format

**Direct Mode:**

```
add <amount> <category> <date in yyyy-mm-dd>
```

**Walkthrough Mode:**

```
add
```

---

#### Implementation Overview

The `AddCommand` class handles both modes. When executed, the command checks whether arguments were supplied:

- **Arguments present** → Direct mode is executed
- **Arguments absent** → Walkthrough mode is triggered

In both cases, a valid `Expense` object is created and added to the `ExpenseList`. After insertion, the user interface
displays a confirmation message and the updated number of expenses.

---

#### Direct Mode

In direct mode, the system parses and validates the input parameters:

| Validation Rule | Requirement                                    |
|-----------------|------------------------------------------------|
| **Amount**      | Must be a positive number                      |
| **Category**    | Must be a non-empty string                     |
| **Date**        | Must follow the required format (`yyyy-mm-dd`) |

**If validation succeeds:**

1. An `Expense` object is created
2. The expense is added to the `ExpenseList`
3. The budget status is updated via the `Limit` component
   `. A confirmation message is displayed

---

#### Walkthrough Mode

If the command is issued without parameters, the system enters an interactive mode. The user is sequentially prompted
for:

1. Expense amount
2. Expense category
3. Expense date

Each input is validated immediately. Invalid input results in an error message and a repeated prompt until valid data is
provided.

**After collecting all inputs:**

- If the user confirms, the expense is added
- Otherwise, the operation is canceled

---

#### Sequence of Operations

The following diagram illustrates the interaction between system components when executing the `add` command in both
direct and walkthrough modes.

![Add Expense Sequence Diagram](UML_diagrams/images/AddCommand.png)

---

#### Design Considerations

| Principle                                      | Benefits                                                                                                                                               |
|------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Single command supporting two modes**        | Improves usability by accommodating different user preferences. Avoids duplicating logic across multiple commands. Keeps the command interface simple. |
| **Interactive validation in walkthrough mode** | Ensures invalid data is handled immediately. Reduces the likelihood of user errors. Provides a guided experience for new users.                        |
| **Separation of concerns**                     | `Ui` handles user interaction. `Parser` interprets input. `AddCommand` performs application logic. `ExpenseList` manages stored expenses.              |

---

#### Limitations

| Limitation                                                      | Impact                                                      |
|-----------------------------------------------------------------|-------------------------------------------------------------|
| Walkthrough mode requires multiple user inputs                  | May be slower for experienced users                         |
| Direct mode requires users to remember the exact command format | Potential for input errors if users don't recall the format |

---
---

### View Expense Feature

The `view` command retrieves and displays expenses from the system. It supports two modes of operation:

1. View all expenses — displays every recorded expense
2. View by category — displays only expenses matching the specified category

This dual behavior allows users to either get a full overview or focus on specific spending categories.

#### Command Format

View all expenses: `view all`

View by category: `view <category>`

#### Implementation Overview

The `ViewCommand` class is responsible for handling both modes. When executed, the command checks the argument supplied:

- If argument is `all` — all expenses are retrieved and displayed
- If argument is a category name — only matching expenses are retrieved and displayed
- If argument is empty — an error is thrown

In both valid cases, a `List<Expense>` is retrieved and passed to `Ui` for display. The total expenditure is calculated
and shown at the end.

#### Sequence of Operations

The following diagram illustrates the interaction between system components when executing the `view` command.

![View Expense Sequence Diagram](UML_diagrams/images/ViewCommand.png)

#### Mode: View All

When the user runs `view all`, the system retrieves the full expense list:

1. `ExpenseList.getAll()` returns every stored expense
2. `Ui.showAllExpenses()` iterates through the list and prints each expense
3. The total expenditure is computed and displayed

#### Mode: View by Category

When the user runs `view <category>`, the system filters expenses:

1. `ExpenseList.getCategoryExpenses(arg)` iterates through all expenses and returns only those matching the category
2. If the result is empty, a `FinbroException` is thrown
3. Otherwise, `Ui.showAllExpenses()` displays the filtered list and total

#### Design Considerations

Single command supporting two view modes

- Avoids the need for separate commands for full and filtered views
- Keeps the command interface simple and intuitive
- Reuses the same `showAllExpenses()` method for both modes

Category matching behavior

- Uses exact string matching via `equals()`
- Ensures predictable and consistent results
- Partial matches are not supported to avoid ambiguous results

Separation of concerns

- `Ui` handles display logic
- `Parser` interprets the input
- `ViewCommand` contains the branching logic
- `ExpenseList` manages data retrieval

#### Limitations

- There is no partial or fuzzy search support
- Users must remember the exact category name used when adding the expense

---
---

### Delete Expense Feature

The `delete` command removes an existing expense from the system. It supports two modes of operation:

1. **Direct Mode** — The category and expense number are provided in a single command
2. **Walkthrough Mode** — The system interactively prompts the user for the required input

This dual behavior improves usability by supporting both experienced users (fast deletion) and new users (guided
deletion).

---

#### Command Format

**Direct Mode:**

```
delete <category> <number>
```

**Walkthrough Mode:**

```
delete
```

---

#### Implementation Overview

The `DeleteCommand` class handles both modes. When executed, the command checks whether arguments were supplied:

- **Arguments present** → Direct mode is executed
- **Arguments absent** → Walkthrough mode is triggered

In direct mode, the target expense is validated and removed immediately. In walkthrough mode, the system first guides
the user through selecting an expense, then removes it only if the user confirms the deletion. After a successful
deletion, the user interface displays a confirmation message and the updated number of expenses.

---

#### Direct Mode

In direct mode, the system parses and validates the input parameters:

| Validation Rule    | Requirement                                |
|--------------------|--------------------------------------------|
| **Category**       | Must refer to an existing expense category |
| **Expense Number** | Must be a valid positive integer           |
| **Command Format** | Must follow `delete <category> <number>`   |

**If validation succeeds:**

1. The category is extracted from the command
2. The expense number is parsed and validated
3. The corresponding expense is removed from the `ExpenseList`
   `. A confirmation message is displayed

---

#### Walkthrough Mode

If the command is issued without parameters, the system enters an interactive mode. The user is sequentially prompted
for:

1. Expense category
2. Expense number within that category
3. Deletion confirmation

Each input is validated before proceeding. Invalid input results in an error message and a repeated prompt until valid
data is provided.

**After collecting all inputs:**

- If the user confirms, the expense is deleted
- Otherwise, the operation is canceled

---

#### Sequence of Operations

The following diagram illustrates the interaction between system components when executing the `delete` command in both
direct and walkthrough modes.

![Delete Expense Sequence Diagram](UML_diagrams/images/DeleteCommand.png)

---

#### Design Considerations

| Principle                               | Benefits                                                                                                                                               |
|-----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Single command supporting two modes** | Improves usability by accommodating different user preferences. Avoids duplicating logic across multiple commands. Keeps the command interface simple. |
| **Confirmation in walkthrough mode**    | Reduces the risk of accidental deletion. Gives users a final chance to verify the selected expense before removal.                                     |
| **Separation of concerns**              | `Ui` handles user interaction. `Parser` interprets input. `DeleteCommand` performs deletion logic. `ExpenseList` manages stored expenses.              |

---

#### Limitations

| Limitation                                                                 | Impact                                                                       |
|----------------------------------------------------------------------------|------------------------------------------------------------------------------|
| Walkthrough mode requires multiple user inputs                             | May be slower for experienced users                                          |
| Direct mode requires users to know the correct category and expense number | Potential for input errors if users do not remember the exact item to remove |
| Direct mode does not include a confirmation step                           | Incorrect input may lead to immediate deletion                               |

---

### Storage Component

The `Storage` class is responsible for persisting expense data and the budget limit across sessions. It reads from and
writes to a local `.txt` file.

#### Load Operation

When the application starts, `Storage.load()` is called:

1. If the file does not exist, an empty list is returned and a new file will be created on the next save
2. The first line is passed to `readLimit()` which checks for the `LIMIT | <value>` format and sets the budget limit via
   `Limit.setLimit()`
3. If the first line is not a limit entry, it is treated as an expense line instead
   `. Each subsequent line is passed to `processExpenseLine()` which splits by `|` and validates the format and amount
   before adding to the list
4. Corrupted or malformed lines are logged and skipped without crashing the application

#### Save Operation

After every command, `Storage.save()` is called:

1. The budget limit is written first in the format `LIMIT | <value>`
2. Each expense is written in the format `amount | category | date`
3. The file and its parent directories are created automatically if they do not exist

#### File Format

The `finbro.txt` file follows this structure:

LIMIT | 1000.00
50.00 | food | 2026-03-01
20.00 | transport | 2026-03-02

#### Design Considerations

Corruption handling

- Malformed lines are skipped rather than throwing an exception
- Ensures a single corrupted entry does not affect the rest of the data
- All skipped lines are logged at WARNING or SEVERE level for debugging

Limit stored as first line

- Separating the limit from expense entries allows it to be read and applied before any expenses are processed
- Falls back gracefully if no limit line is found

Flat file over a database

- Keeps the application lightweight with no external dependencies
- Sufficient for the scale of data this application handles

---

### Currency Exchange Feature

The `currency` command allows users to convert an existing expense amount from one currency to another. It follows an
interactive workflow where users specify the source currency, target currency, and the expense entry to convert.

This feature operates entirely offline using predefined exchange rates stored in the system.

```
currency
```

---

#### Implementation Overview

The `CurrencyCommand` class manages the full workflow of currency conversion. It interacts with the following
components:

| Component               | Responsibility                                                |
|-------------------------|---------------------------------------------------------------|
| **`CurrencyCommand`**   | Handles user interaction, validation, and conversion workflow |
| **`CurrencyRateTable`** | Stores exchange rates and performs conversion logic           |
| **`ExpenseList`**       | Provides access to stored expenses                            |
| **`Ui`**                | Handles user input and output display                         |

---

#### Currency Conversion Logic

The `CurrencyRateTable` uses **SGD as the base currency**. All exchange rates are defined relative to SGD.

Conversion follows these rules:

| Case             | Logic                  |
|------------------|------------------------|
| Same currency    | Return original amount |
| From SGD         | `amount × rate`        |
| To SGD           | `amount ÷ rate`        |
| Other currencies | Convert via SGD        |

---

#### Sequence Diagram

The following diagram illustrates the interaction between system components when executing the `currency` command.
![CurrencyCommand Sequence Diagram](UML_diagrams/images/CurrencyCommand.png)

---

#### Design Considerations

| Principle                  | Benefits                                                 |
|----------------------------|----------------------------------------------------------|
| **Interactive workflow**   | Easier for users; no need to memorise command syntax     |
| **Separation of concerns** | Conversion logic isolated in `CurrencyRateTable`         |
| **Base currency approach** | Reduces complexity and avoids storing all currency pairs |
| **Offline capability**     | No dependency on APIs or internet                        |
| **Logging support**        | Improves debugging and traceability                      |

---

#### Error Handling

The system validates multiple error conditions:

| Error Case            | Behaviour                        |
|-----------------------|----------------------------------|
| No expenses available | Throws `FinbroException`         |
| Unsupported currency  | Displays supported currency list |
| Invalid entry number  | Throws `FinbroException`         |
| Entry out of range    | Throws `FinbroException`         |

---

### Visualisation Feature

The `visual` command creates a bar graph of the user's spendings arranged by month. 

#### Sequence of operations 

![VisualCommandUML](UML_diagrams/images/VisualCommand.png)

Flow:

| Step | Action | 
| ---- | ------ | 
| 1 | `Finbro` calls execute on `VisualCommand` object | 
| 2 | The command object gets the monthly expenses and sorts them by month | 
| 3 | Assembles the bar chart and assembles into an output string | 
| 4 | Passes the output to `Ui` which shows it to the user | 

#### Implementation overview 

Sorting: 

- `getMonthlyExpenses()` returns a `Map` with the year and month as the key, and the amount as the value
- `VisualCommand` places the output in a `TreeMap` which will sort the expenses by key (year and month)

Bar graph construction: 

- Bar graph is constructed using the full block Unicode character - █
- The month with the largest expense is set to have `MAX_BAR_LENGTH` number of bars 
- Other months will have a number of bars roughly equal to its proportion of the largest monthly expense

## Product Scope

### Target User Profile

This application is optimized for users who:

- Prefer fast keyboard input over graphical interfaces
- Want to track personal expenses efficiently
- Need quick access to spending patterns and limits

---

### Value Proposition

This application helps users keep track of their spending and provides frequent reminders to prevent unnecessary
expenditures.

---

## User Stories

| Version | As a...      | I want to...                                              | So that I can...                                         |
|---------|--------------|-----------------------------------------------------------|----------------------------------------------------------|
| v1.0    | new user     | see usage instructions                                    | refer to them when I forget how to use the application   |
| v1.0    | new user     | record an expense by providing only amount and category   | start tracking my spending without learning many details |
| v1.0    | new user     | see clear error messages when I enter invalid inputs      | correct mistakes without frustration                     |
| v1.0    | new user     | view a short help guide explaining available commands     | understand how to use the application                    |
| v1.0    | regular user | record expenses with a description and a date             | have an accurate and meaningful spending history         |
| v1.0    | regular user | be able to delete my expenses                             | remove unnecessary expenses                              |
| v1.0    | regular user | view total spending by category                           | understand where my money is going                       |
| v1.0    | regular user | set spending limits for a week/month and receive warnings | avoid overspending                                       |

---

## Non-Functional Requirements

- **Performance** — The application should respond to user commands within 1 second under normal load
- **Reliability** — Data should be persisted reliably without loss between sessions
- **Usability** — Commands should be intuitive for users familiar with CLI applications
- **Portability** — The application should run on any platform with Java #### or higher installed
- **Maintainability** — Code should follow clean architecture principles for easy maintenance and extension

---

## Glossary

- **Expense** — A record of money spent, including amount, category, and date
- **Limit** — A spending threshold set by the user to track budget compliance
- **Direct Mode** — Command execution with all parameters provided upfront
- **Walkthrough Mode** — Interactive command execution with step-by-step prompts

---

## Instructions for Manual Testing

### Loading Sample Data

1. Launch the application
2. Use the `add` command to create sample expenses:
   ```
   add 50.00 Food 2026-01-15
   add 120.00 Transport 2026-01-16
   add 30.50 Entertainment 2026-01-17
   ```
3. Set a spending limit:
   ```
   limit 500
   ```

### Testing Core Features

**Adding Expenses:**

- Test direct mode: `add 25.00 Groceries 2026-01-20`
- Test walkthrough mode: `add` (then follow prompts)
- Test invalid inputs (negative amounts, invalid dates)

**Managing Limits:**

- Set a new limit: `limit 1000`
- Edit the existing limit: `edit limit`
- Test confirmation flows (accept/decline)

**Viewing Data:**

- List all expenses
- View expenses by category
- Check budget status against limit

---
