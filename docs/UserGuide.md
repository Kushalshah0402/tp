# User Guide

## Introduction

**Finbro** is a simple and efficient personal finance tracker that helps users record, manage, and monitor their
expenses. It allows you to keep track of spending, set financial limits, and convert expenses into different currencies
for better financial awareness.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `Finbro` from the provided release link.
3. Open a terminal in the folder containing the `.jar` file.
4. Run the application using:

   java -jar finbro.jar

5. Start entering commands to manage your expenses.

## Features

### Editing the spending limit: `edit limit`

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
| Delete     | `delete <index>`                 | Deletes an expense (direct input) |
| Delete     | `delete`                         | Deletes an expense (guided input) |
| Set Limit  | `limit`                          | Sets a monthly spending limit     |
| Edit Limit | `edit limit`                     | Edits the current spending limit  |
| Currency   | `currency`                       | Converts expense currency         |
| Help       | `help`                           | Shows help information            |
