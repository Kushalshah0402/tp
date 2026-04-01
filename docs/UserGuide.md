# User Guide

## Introduction

**Finbro** is a simple and efficient personal finance tracker that helps users record, manage, and monitor their
expenses. It allows you to keep track of spending, set financial limits, and convert expenses into different currencies
for better financial awareness.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `Finbro` from the provided release link.
3. Open a terminal in the folder containing the `.jar` file.
`. Run the application using:

   java -jar finbro.jar

5. Start entering commands to manage your expenses.

## Features
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

## Add Expense Command

The `add` command lets you record a new expense. Whether you're in a hurry or want to take your time, we've got you covered.


### Direct Mode

**Use this when you know exactly what you want to enter:**

```
add <amount> <category> <date>
```

**Required Information:**

| Field | Format | Example |
|-------|--------|---------|
| **Amount** | Positive number | `50.00` or `25` |
| **Category** | Text (no spaces) | `Groceries` |
| **Date** | YYYY-MM-DD | `202`-01-20` or `today, last week, 2 days ago, last monday` |

**Example:**
```
add 50.00 Groceries 202`-01-20
```
- Type `yes` to confirm or `no` to cancel
---

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

---

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

---

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
---

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

---

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

The `view` command allows you to display your recorded expenses. You can either view all expenses at once or filter by a specific category.

### Command Format

`view all` — displays all recorded expenses

`view <category>` — displays expenses under a specific category

### Examples

`view all`

Expected output:

Here are your expenses:

1.  Amount: $50.00
    Category: food
    Date: 2026-03-01

2.  Amount: $20.00
    Category: transport
    Date: 2026-03-02

Total expenditure: $70.00

`view food`

Expected output:

Here are your expenses:

1.  Amount: $50.00
    Category: food
    Date: 2026-03-01

Total expenditure: $50.00

### Notes
- The categories are NOT case sensitive so you can write `view Food` or `view food` to get the same output
- If no expenses exist under the specified category, an error message will be shown
- Running `view` without any argument will display an error message prompting the correct format

---

## Editing the spending limit: `edit limit`

Allows you to modify your current monthly spending limit by increasing, decreasing, or replacing it.

**Format:** `edit limit`

---

#### Description

When this command is executed, the system will display your current spending limit and prompt you to choose one of the
following options by keying in the option number:

1. Increase limit
2. Decrease limit
3. Replace limit

After selecting an option, you will be prompted to enter the amount for the chosen operation.  
You will then be asked to confirm the change before the new limit is applied.

---

#### Behaviour

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

### Converting expense currency: `currency`

Allows you to convert an existing expense into another currency using predefined exchange rates.

**Format:** `currency`

---

#### Description

When this command is executed, the system will prompt you to enter:

1. Source currency (e.g. SGD, USD, EUR, GBP, JPY, CNY, AUD, CAD, MYR, HKD, KRW)
2. Target currency (e.g. SGD, USD, EUR, GBP, JPY, CNY, AUD, CAD, MYR, HKD, KRW)

After entering both currencies, a list of recorded expenses will be displayed.  
You will then select the expense you wish to convert by entering its index.

The system will convert the selected expense amount into the target currency and display the result.

---

#### Behaviour

* Uses a **local currency rate table** (no internet connection required).
* Supports conversion between multiple currencies via SGD as a base.
* Only valid and supported currency codes are accepted.
* The selected expense must exist in the list.
* The converted value is displayed but does not modify the original expense.

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


## Command Summary

| Command    | Format                           | Description                       |
|------------|----------------------------------|-----------------------------------|
| Add        | `add <amount> <category> <date>` | Adds a new expense (direct input) |
| Add        | `add`                            | Adds a new expense (guided input) |
| View       | `view`                           | Displays all expenses             |
| Delete     | `delete <category> <index>`      | Deletes an expense (direct input) |
| Delete     | `delete`                         | Deletes an expense (guided input) |
| Set Limit  | `limit`                          | Sets a monthly spending limit     |
| Edit Limit | `edit limit`                     | Edits the current spending limit  |
| Currency   | `currency`                       | Converts expense currency         |
| Help       | `help`                           | Shows help information            |
| Help       | `help <command>`                 | Shows detailed help for a specific command            |
