# User Guide

## Table of Contents
- [Introduction](#introduction)
- [Quick Start](#quick-start)
- [Features](#features)
  - [Help Command](#help-command)
  - [Add Expense Command](#add-expense-command)
  - [Delete Expense Command](#delete-expense-command)
  - [View Expenses](#view-expenses)
  - [Editing the spending limit: `edit limit`](#editing-the-spending-limit-edit-limit)
  - [Budget Reminder System](#budget-reminder-system)
  - [Converting expense currency: `currency`](#converting-expense-currency-currency)
- [FAQ](#faq)
- [Command Summary](#command-summary)

---

## Introduction

**Finbro** is a simple and efficient personal finance tracker that helps users record, manage, and monitor their
expenses. It allows you to keep track of spending, set financial limits, and convert expenses into different currencies
for better financial awareness.

---

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `Finbro` from the provided release link.
3. Open a terminal in the folder containing the `.jar` file. Run the application using:

   `java -jar finbro.jar`

4. Start entering commands to manage your expenses.

---

# Features
## Help Command

The `help` command displays usage information for available commands in Finbro.  
You can use it either to see the general command list or to view detailed help for a specific command.

### General Help

**Use this when you want to see all available commands:**

```
help
```
### Command-Specific Help
Use this when you want more detailed help for a specific command:
```
help <command>
```
Supported examples:

 - help add
 - help delete
 - help view
 - help limit
 - help edit limit
 - help currency
 - help visual

---

## Add Expense Command

The `add` command lets you record a new expense. Whether you're in a hurry or want to take your time, we've got you covered.


### Direct Mode

**Use this when you know exactly what you want to enter:**

```
add <amount> <category> <date>
```

**Required Information:**

| Field        | Format           | Example                                                     |
|--------------|------------------|-------------------------------------------------------------|
| **Amount**   | Positive number  | `50.00` or `25`                                             |
| **Category** | Text (no spaces) | `Groceries`                                                 |
| **Date**     | YYYY-MM-DD       | `202`-01-20` or `today, last week, 2 days ago, last monday` |

**Example:**
```
add 50.00 Groceries 202`-01-20
```
- Type `yes` to confirm or `no` to cancel

### Walkthrough Mode

**Use this when you want the system to guide you:**

Simply type:
```
add
```

The system will ask you for:

1. **Expense Amount**
   - Enter how much you spent
   - Must be a positive number
   - Example: `50.00` or `25`

2. **Expense Category**
   - What did you spend on?
   - Examples: `Food`, `Transport`, `Entertainment`, `Shopping`

3. **Expense Date**
   - When did you spend this?
   - Use format: Date format shown above

`. **Confirmation**
   - Review your entry
   - Type `yes` to confirm or `no` to cancel

### Examples

**Example 1: Adding groceries in direct mode**
```
add `5.50 Groceries 202`-01-—
```

**Example 2: Adding a transport expense in walkthrough mode**
```
add
> Enter amount: 15.00
> Enter category: Transport
> Enter the date (yyyy-MM-dd or today): 3 days ago
> Confirm entry? (yes/no): yes
```

**Example 3: Quick coffee purchase**
```
add 5.00 Food yesterday
```

### Common Issues

**❌ Error: "Amount must be a positive number"**
- Make sure you entered a valid number
- Negative numbers are not allowed
- Decimal numbers are okay (e.g., `12.50`)

**❌ Error: "Invalid date format"**
- Use the format: `YYYY-MM-DD`
- Examples: `202`-01-20`, `202`-12-31`
- Make sure the date is valid

**❌ Error: "Category cannot be empty"**
- Enter a category name (no numbers or special characters)
- Examples: `Food`, `Transport`, `Rent`, `Entertainment`

**❌ "I made a mistake"**
- In walkthrough mode, type `no` when asked to confirm
- You can delete the expense and add a new one
- Or use the `delete` command to remove the incorrect entry

---
## Delete Expense Command

The `delete` command lets you remove an existing expense. Similar to the `add` command, it supports both direct mode and walkthrough mode.


### Direct Mode

**Use this when you know exactly what you want to remove:**

```
delete <category> <index>
```

**Required Information:**

| Field        | Format           | Example                                                     |
|--------------|------------------|-------------------------------------------------------------|
| **category** | Text (no spaces) | `Groceries`                                                 |
| **Index**    | Positive Number  | 1                                                           |


**Example:**
```
delete food 1
```

### Walkthrough Mode

**Use this when you want the system to guide you:**

Simply type:
```
delete
```

The system will guide you through the deletion process step by step:

1. **Category**
    - Enter the category of the expense you want to delete
    - The category must already exist
    - Type `-l` to display all existing category names

2. **Expense Number**
    - Enter the index number of the expense in that category
    - The number must be a positive integer within the valid range
    - Type `-l` to display all expenses under the selected category

3. **Confirmation**
    - Review the expense to be deleted
    - Type `yes` or `y` to confirm deletion
    - Any other input cancels the deletion

### Examples

**Example 1: Deleting an expense in direct mode**
```
delete food 1
```

**Example 2: Deleting an expense in walkthrough mode**
```
delete
> Enter category name, or -l to list all categories:
Food
> Enter the expense index to delete, or type -l to list expenses in this category.
2
> Confirm delete? (yes/no):
yes
```

**Example 3: Listing categories and expenses in walkthrough mode**
```
delete
> Enter category name, or -l to list all categories:
-l
> Food
> Transport
> Shopping
> Enter category name, or -l to list all categories:
Food
> What is the index of the expense you want to delete?
-l
> 1. $5.00 | 2026-03-10
> 2. $12.50 | 2026-03-12
> What is the index of the expense you want to delete?
1
> Confirm delete? (yes/no):
y
```

---
## View Expenses

The `view` command lets you display your recorded expenses in different ways.
You can view all expenses, view a specific category, or optionally sort and filter the results with `-sort` and `-filter`.

### Command Format

`view all` — displays all recorded expenses

`view all -sort <month|category|amount>` — displays all expenses sorted by the chosen option

`view <category>` — displays expenses under a specific category

`view <category> -filter <month>` — displays only the expenses in that category for the given month

`view <category> -sort <month|amount>` — displays that category sorted by the chosen option

`view <category> -filter <month> -sort <month|amount>` — filters the category by month first, then sorts the result

### How it works

- `view all` shows every recorded expense.
- `view all` can be sorted with `-sort month`, `-sort category`, or `-sort amount`.
- `view <category>` shows only expenses that match the category name.
- `view <category>` can be filtered by month with `-filter <month>`.
- `-filter` is case-insensitive, so `january`, `January`, and `JANUARY` are treated the same.
- `view <category>` can also be sorted with `-sort month` or `-sort amount`.
- You can use `-filter` and `-sort` together, and the order of the flags does not matter.
- `-sort category` is only supported with `view all`.
- `-filter` only works with `view <category>`.

### Examples

**Example 1: Viewing all expenses**

`view all`

Expected output:
```
Here are your expenses:

1.  Amount: $50.00
    Category: food
    Date: 2026-03-01

2.  Amount: $20.00
    Category: transport
    Date: 2026-03-02

Total expenditure: $70.00
```

**Example 2: Viewing expenses under the `food` category**

`view food`:

Expected output:
```
Here are your expenses:

1.  Amount: $50.00
    Category: food
    Date: 2026-03-01

Total expenditure: $50.00
```

**Example 3: Viewing all expenses sorted by amount**

`view all -sort amount`

Expected output:

```
Here are your expenses:

1.  Amount: $50.00
    Category: food
    Date: 2026-03-01

2.  Amount: $20.00
    Category: transport
    Date: 2026-03-02

Total expenditure: $70.00
```


**Example 4: Viewing expenses by category and filtering by month**

`view transport -filter march`

Expected output:

```
Here are your expenses:

1.  Amount: $50.00
    Category: transport
    Date: 2026-03-01

Total expenditure: $50.00
```

**Example 5: Viewing expenses by category, filtering by month, and sorting by amount**

`view transport -filter march -sort amount`

Expected output:
```
Here are your expenses:

1.  Amount: $50.00
    Category: transport
    Date: 2026-03-01

2.  Amount: $20.00
    Category: transport
    Date: 2026-03-12

Total expenditure: $70.00
```

### Notes
- The categories are NOT case-sensitive so you can write `view Food` or `view food` to get the same output.
- The month used with `-filter` is also case-insensitive, so `view transport -filter JANUARY` works.
- `-filter` only accepts a month name, such as `January`.
- If no expenses exist under the specified category, an error message will be shown.
- Running `view` without any argument will display an error message prompting the correct format
- Each flag may appear at most once.

---

## Setting the spending limit: `limit`

Allows you to set a new monthly spending limit. 

**Format** `limit <amount>`

- Amount must be a numeric input
- Amount must be non-negative
- System will prompt for a confirmation message - "yes"
- Limit will be saved on disk between Finbro sessions

**Example:** `limit 100`

Output: 
```
Are you sure you want to change your monthly budget limit to $100.00? [yes/no]
yes
--------------------------------------------------
Monthly budget limit: $100.00
--------------------------------------------------
```

---

## Editing the spending limit: `edit limit`

Allows you to modify your current monthly spending limit by increasing, decreasing, or replacing it.

**Format:** `edit limit`

### Description

When this command is executed, the system will display your current spending limit and prompt you to choose one of the
following options by keying in the option number:

1. Increase limit
2. Decrease limit
3. Replace limit

After selecting an option, you will be prompted to enter the amount for the chosen operation.  
You will then be asked to confirm the change before the new limit is applied.

### Behaviour

* **Increase limit**
    * Adds the entered amount to the current limit.
* **Decrease limit**
    * Subtracts the entered amount from the current limit.
    * The resulting limit **must not be negative**.
* **Replace limit**
    * Sets the current limit to a new value.
* All inputs must be **valid non-negative numbers**.
* The system will reject invalid menu selections or invalid numeric inputs.

---
## Budget Reminder System

Finbro includes a budget reminder system that alerts you when your total expenses exceed your set monthly limit, 
or is close to exceeding the limit.  
When you add a new expense, the system will check if the total expenditure for the current month differs by $20 
or below with the limit, or exceeds the limit.
If it does, a warning message will be displayed to inform you that you are close to exceeding your monthly budget,
or have exceeded your budget for the month.

### Examples

**Example 1: Close to limit warning**
Suppose your monthly spending limit is set to \$100.00, and your total expenses for the current month have reached 
\$85.00. The system will check the total expenditure (\$85.00) against the limit (\$100.00), and constantly prompt you
with the following warning message until you add more expenses or edit your limit:
```
Warning: You are close to your monthly spending limit of $100.00!
```

**Example 2: Exceeded limit warning**
Suppose your monthly spending limit is set to \$100.00, and your total expenses for the current month have reached 
\$120.00. The system will check the total expenditure (\$120.00) against the limit (\$100.00), and constantly prompt 
you with the following warning message until you add more expenses or edit your limit:
```
Warning: You have exceeded your monthly spending limit of $100.00!
```

---

## Converting expense currency: `currency`

Allows you to convert an existing expense into another currency using predefined exchange rates.

**Format:** `currency`

### Description

When this command is executed, the system will prompt you to enter:

1. Source currency (e.g. SGD, USD, EUR, GBP, JPY, CNY, AUD, CAD, MYR, HKD, KRW)
2. Target currency (e.g. SGD, USD, EUR, GBP, JPY, CNY, AUD, CAD, MYR, HKD, KRW)

After entering both currencies, a list of recorded expenses will be displayed.  
You will then select the expense you wish to convert by entering its index.

The system will convert the selected expense amount into the target currency and display the result.

### Behaviour

* Uses a **local currency rate table** (no internet connection required).
* Supports conversion between multiple currencies via SGD as a base.
* Only valid and supported currency codes are accepted.
* The selected expense must exist in the list.
* The converted value is displayed but does not modify the original expense.

---

## Visualization: `visual`

Allows you to view a visualization of your monthly spendings.

**Format:** `visual`

**Example Output:**
```
--------------------------------------------------
=== Monthly Spendings ===
Jan 2026 | ████                 $10.50
Feb 2026 | ████████             $20.00
Mar 2026 | ████████████████████ $50.00
Apr 2026 | ██                   $5.00
--------------------------------------------------
```

---

## FAQ

**Q**: Can I cancel the `edit limit` operation after entering the amount?  
**A**: Yes. After entering the amount, you will be asked to confirm the change. Enter `no` to cancel and keep your
current limit unchanged.

---

**Q**: Do currency conversions update my stored expenses?  
**A**: No. The `currency` command only displays the converted value. Your original expense remains unchanged.

---

**Q**: Why are the currency rates different from real-world rates?  
**A**: Finbro uses a local predefined rate table and does not fetch live exchange rates.

---

**Q**: What should I do if my currency is not supported?  
**A**: Use one of the supported currencies listed in the error message when prompted.

---

## Command Summary

| Command    | Format                                            | Description                                                     |
|------------|---------------------------------------------------|-----------------------------------------------------------------|
| Add        | `add <amount> <category> <date>`                  | Adds a new expense (direct input)                               |
| Add        | `add`                                             | Adds a new expense (guided input)                               |
| View       | `view all` with optional `-sort`                  | Displays all expenses with optional sorting                     |
| View       | `view <category>` with optional `-sort`/`-filter` | Displays a category of expenses with optional sorting/filtering |
| Delete     | `delete <category> <index>`                       | Deletes an expense (direct input)                               |
| Delete     | `delete`                                          | Deletes an expense (guided input)                               |
| Set Limit  | `limit`                                           | Sets a monthly spending limit                                   |
| Edit Limit | `edit limit`                                      | Edits the current spending limit                                |
| Currency   | `currency`                                        | Converts expense currency                                       |
| Visualize  | `visual`                                          | Shows a bar chart of monthly expenses                           |
| Help       | `help`                                            | Shows help information                                          |
| Help       | `help <command>`                                  | Shows detailed help for a specific command                      |
