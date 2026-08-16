# User stories — Mi Diario

## HU-01 — Add a lived moment

**As a** user
**I want** to add a lived moment with its title, description, emotion and date
**So that** I can remember it whenever I need to

**Acceptance criteria**

- **Scenario 1: Add a moment with valid data**
  - **Given** I am on the main menu
  - **When** I select "Add moment" and enter a title, a valid date (dd/mm/yyyy), a description and an emotion from 1 to 10
  - **Then** the moment is saved with a unique id and its creation/modification dates, and "Momento vivído añadido correctamente." is shown

- **Scenario 2: Invalid date format**
  - **Given** I am adding a moment
  - **When** I enter the date in a format other than dd/mm/yyyy
  - **Then** an error message indicating the correct format is shown and I am asked to enter the date again

- **Scenario 3: Emotion out of range**
  - **Given** I am adding a moment
  - **When** I select an emotion option that is not between 1 and 10
  - **Then** an error message is shown and I am asked to select the emotion again

- **Scenario 4: Empty required fields**
  - **Given** I am adding a moment
  - **When** I leave the title or description empty
  - **Then** an error message indicating that field is required is shown and I cannot continue until I provide a value

## HU-02 — View all moments

**As a** user
**I want** to retrieve the list of registered moments
**So that** I can look back on them

**Acceptance criteria**

- **Scenario 1: List existing moments**
  - **Given** there is at least one moment registered
  - **When** I select "View all available moments"
  - **Then** the list is shown with id, date, title, description and emotion for each moment

- **Scenario 2: List when there are no moments**
  - **Given** there are no moments registered
  - **When** I select "View all available moments"
  - **Then** a message indicating there are no moments yet is shown

## HU-03 — Delete a moment

**As a** user
**I want** to delete a lived moment
**So that** I can avoid duplicates and keep the list organized

**Acceptance criteria**

- **Scenario 1: Delete an existing moment**
  - **Given** a moment with id 1 exists
  - **When** I select "Delete a moment" and enter id 1
  - **Then** the moment is deleted and "Momento vivído eliminado correctamente." is shown

- **Scenario 2: Delete a non-existent id**
  - **Given** no moment with id 99 exists
  - **When** I enter id 99 to delete
  - **Then** nothing is deleted and a message indicating that no moment exists with that id is shown

- **Scenario 3: Non-numeric id**
  - **Given** I am deleting a moment
  - **When** I enter an id that is not a number
  - **Then** an error message is shown and nothing is deleted

## HU-04 — Filter by emotion

**As a** user
**I want** to retrieve the lived moments by their emotion
**So that** I can view them grouped by what I felt

**Acceptance criteria**

- **Scenario 1: Filter by an emotion with results**
  - **Given** moments with the emotion "Alegría" exist
  - **When** I select "Filter moments", choose to filter by emotion and select "Alegría"
  - **Then** only the moments with emotion Alegría are shown

- **Scenario 2: Filter by an emotion with no results**
  - **Given** no moment with the emotion "Envidia" exists
  - **When** I filter by the emotion "Envidia"
  - **Then** a message indicating there are no moments with that emotion is shown

- **Scenario 3: Emotion out of range**
  - **Given** I am filtering by emotion
  - **When** I select an option that is not between 1 and 10
  - **Then** an error message is shown

- **Scenario 4: Non-numeric input**
  - **Given** I am filtering by emotion
  - **When** I enter text that doesn't correspond to any emotion
  - **Then** an error message is shown

## HU-05 — Filter by date

**As a** user
**I want** to retrieve the lived moments from a specific date
**So that** I can look back on what I lived that day

**Acceptance criteria**

- **Scenario 1: Filter by a date with results**
  - **Given** a moment with date 15/05/2024 exists
  - **When** I select "Filter moments", choose to filter by date and enter "15/05/2024"
  - **Then** only the moments that happened on that date are shown

- **Scenario 2: Filter by a date with no results**
  - **Given** no moment exists on the given date
  - **When** I filter by the date "01/01/2020"
  - **Then** a message indicating there are no moments on that date is shown

- **Scenario 3: Invalid date format**
  - **Given** I am filtering by date
  - **When** I enter the date in a format other than dd/mm/yyyy
  - **Then** an error message indicating the correct format is shown

## HU-06 — Exit the program

**As a** user
**I want** to exit the program
**So that** I can close it or start it again

**Acceptance criteria**

- **Scenario 1: Exit the program**
  - **Given** I am on the main menu
  - **When** I select "Exit"
  - **Then** "Hasta la próxima!!!" is shown and the program terminates cleanly