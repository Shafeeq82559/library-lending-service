# Product Requirements: Community Library Lending Platform

**Owner:** Product, Library Services
**Status:** Approved for v1
**Last updated:** November 2026

## Background

Our branch libraries still track lending on paper cards and spreadsheets. Staff lose time reconciling who has which book, late fees are applied inconsistently, and members can't see what's on the shelf without calling in. v1 replaces this with a single lending platform for the front desk, and later for members directly.

## Goals

- Give staff one accurate view of the collection and of who has what.
- Apply lending rules the same way at every branch.
- Reduce lost revenue from late returns.

## Users

- **Front-desk staff:** manage the collection, check books in and out, deal with problem accounts.
- **Members:** borrow books and manage their own account.
- **Branch managers:** look at usage trends to plan purchasing.

## Requirements

### Collection

**R1. Adding titles to the collection**
Staff must be able to add a new title to the collection, recording its ISBN, title, author and how many physical copies the branch owns. Genre and year of publication are optional. Each ISBN may appear in the collection only once. Entering an ISBN we already have should be refused with a clear message.

**R6. Finding books**
Members and staff should be able to type part of a title or an author's name and get back every matching book. Results should be listed in alphabetical order by title so long result lists are easy to scan.

**R12. Taking copies out of circulation**
When a copy is lost or damaged beyond repair, staff need to take it out of circulation. Copies taken out of circulation must no longer count as available and must not be lent out.

### Lending

**R2. Checking out a book**
A member can take a book home when at least one copy is on the shelf. Once a copy is lent out, the shelf count for that title should go down by one. If no copies are left, the checkout should be refused. The standard lending period is two weeks.

**R3. Returning a book**
When a member brings a book back, staff record the return. The copy goes back on the shelf and the loan is closed. A loan that is already closed cannot be returned again.

**R4. Borrowing limit**
To keep the collection circulating, no member may have more than five books out at the same time. A sixth checkout should be refused until something is returned.

**R9. More time on a borrowed book**
Members sometimes need longer with a book. A member should be able to get another two weeks on a book they currently have out. This can be done at most twice for the same loan. Books that are already late cannot be given more time; the member must return them first.

**R10. Holds on popular titles**
When every copy of a title is out, a member should be able to put their name down for it. When a copy comes back, the first member in line should get it before anyone else can check it out.

**R11. Due-date reminders**
Members should get an email reminder two days before a book is due back, to cut down on late returns.

### Late fees

**R7. Late fees**
Books returned late are charged a fee of $0.25 for every day past the due date. To keep fees fair, the fee for any single loan must never be more than $10.00, however late the book is. Members and staff should be able to see the fees on an account.

### Membership

**R5. Joining the library**
New members join by giving their full name and email address; a phone number is optional. The email address must be valid, and each email address can belong to only one member.

**R8. Keeping member details accurate**
Member details (name, email and phone) must meet the same quality rules whether a member is joining for the first time or changing their details later. For example, it should not be possible to change an email to something that isn't a valid address, or to blank out a member's name.

**R13. Suspending borrowing privileges**
Staff need to be able to temporarily suspend a member's borrowing privileges, for example after repeated damage to books, and record a short reason. A suspended member cannot check anything out. Staff must be able to reinstate the member later, at which point they can borrow again as normal.

### Reporting

**R14. Most-borrowed titles report**
Branch managers need a monthly report showing which titles were borrowed most often during the month, ranked from most to least borrowed, to guide purchasing decisions.

## Out of scope for v1

- Online payment of fees
- Inter-branch transfers
- E-books and audiobooks
