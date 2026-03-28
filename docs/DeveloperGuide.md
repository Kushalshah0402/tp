# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## Design & implementation

### Limit component

`Limit.java`: stores the limit as a static variable accessible across the application\

- using a static variable prevents inconsistent limit values
- no need to pass a `Limit` object across class methods to check the limit

`SetLimitCommand.java`: handles validation and user confirmation logic\

- improves separation of concerns

`EditLimitCommand.java`: handles the interactive process of modifying an existing monthly spending limit\

#### Setting the limit

The sequence diagram below illustrates the interaction within the `Limit` component, with an input of `limit 100`
from the user.

![Set Limit Sequence Diagram](UML_diagrams/images/SetLimit.png)

How the `Limit` component sets a limit

1. The `Ui` will handle receiving the input and passes that input into `Finbro`.
2. `Finbro` passes the input into `Parser` to parse the input, which creates a `SetLimitCommand` object.
3. Executing the command object will verify the user's input limit and if valid, will get confirmation from the user
   to change the limit.
   `. If user inputs "yes", then the limit will be changes accordingly, otherwise it remains unchanged.
4. `Ui` will show the updated limit.
5. `Finbro` will update the limit in `Storage` file.


#### Editing the limit

The sequence diagram below illustrates the interaction within the `Limit` component, with an input of `edit limit`
from the user.

![Edit Limit Sequence Diagram](UML_diagrams/images/EditLimit.png)

How the `Limit` component edits a limit

1. The `Ui` will handle receiving the input and passes that input into `Finbro`.
2. `Finbro` passes the input into `Parser` to parse the input, which creates an `EditLimitCommand` object.
3. Executing the command object will first retrieve the current limit from `Limit`.
4. `Ui` will display an edit menu for the user to choose whether to increase, decrease, or replace the current limit.
5. The user enters the corresponding amount.
6. `EditLimitCommand` validates the entered amount:
   - the input must be a valid number
   - the input must not be negative
   - if the user chooses to decrease the limit, the resulting limit must not be below `$0`
7. If the input is valid, `EditLimitCommand` computes the new limit and calls the confirmation logic in
   `SetLimitCommand`.
   - If user inputs `"yes"`, then the limit will be changed accordingly, otherwise it remains unchanged.
8. `Ui` will show the updated limit.
9. `Finbro` will update the limit in `Storage` file.

---

## Add Expense Feature

The `add` command records a new expense in the system. It supports two modes of operation:

1. Direct mode — when all required parameters are provided in a single command
2. Walkthrough mode — when no parameters are provided, the system interactively prompts the user for input

This dual behavior improves usability by supporting both experienced users (fast entry) and new users (guided input).

## Command Format

Direct mode:

add `amount` `category` `date`

Walkthrough mode:

add

## Implementation Overview

The `AddCommand` class is responsible for handling both modes. When executed, the command checks whether arguments were supplied:

- If arguments are present — Direct mode is executed
- If arguments are absent — Walkthrough mode is triggered

In both cases, a valid `Expense` object is created and added to the `ExpenseList`. After insertion, the user interface displays a confirmation message and the updated number of expenses.

## Direct Mode

In direct mode, the system parses and validates the input parameters:

- Amount must be a positive number
- Category must be a non-empty string
- Date must follow the required format

If validation succeeds:

1. An `Expense` object is created
2. The expense is added to the `ExpenseList`
3. The budget status is updated via the `Limit` component
4. A confirmation message is displayed

## Walkthrough Mode

If the command is issued without parameters, the system enters an interactive mode. The user is sequentially prompted for:

1. Expense amount
2. Expense category
3. Expense date

Each input is validated immediately. Invalid input results in an error message and a repeated prompt until valid data is provided.

After collecting all inputs, the system asks for confirmation:

- If the user confirms, the expense is added
- Otherwise, the operation is canceled

## Sequence of Operations

The following diagram illustrates the interaction between system components when executing the `add` command in both direct and walkthrough modes.

![Add Expense Sequence Diagram](UML_diagrams/images/AddCommand.png)

## Design Considerations

Single command supporting two modes

- Improves usability by accommodating different user preferences
- Avoids duplicating logic across multiple commands
- Keeps the command interface simple

Interactive validation in walkthrough mode

- Ensures invalid data is handled immediately
- Reduces the likelihood of user errors
- Provides a guided experience for new users

Separation of concerns

- `Ui` handles user interaction
- `Parser` interprets input
- `AddCommand` performs application logic
- `ExpenseList` manages stored expenses

## Limitations

- Walkthrough mode requires multiple user inputs, which may be slower for experienced users
- Direct mode requires users to remember the exact command format

## Product scope

### Target user profile

{Describe the target user profile}

This application is optimized for users who prefer fast keyboard input over graphical interfaces.

### Value proposition

{Describe the value proposition: what problem does it solve?}

This application helps users keep track of their spending's and gives frequent reminders to prevent them from spending
unnecessarily.

## User Stories

| Version | As a ...     | I want to ...                                             | So that I can ...                                           |
|---------|--------------|-----------------------------------------------------------|-------------------------------------------------------------|
| v1.0    | new user     | see usage instructions                                    | refer to them when I forget how to use the application      |
| v1.0    | new user     | record an expense by providing only amount and category   | start tracking my spending without learning many details    |
| v1.0    | new user     | see clear error messages when I enter invalid inputs      | correct mistakes without frustration                        |
| v1.0    | new user     | view a short help guide explaining available commands     | understand how to use the application                       |
| v1.0    | regular user | record expenses with a description and a date             | have an accurate and meaningful spending history            |
| v1.0    | regular user | be able to delete my expenses                             | remove unnecessary expenses                                 |
| v1.0    | regular user | view total spending by category                           | understand where my money is going                          |
| v1.0    | regular user | set spending limits for a week/month and receive warnings | avoid overspending                                          |
| v2.0    | user         | find a to-do item by name                                 | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

- _glossary item_ - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
