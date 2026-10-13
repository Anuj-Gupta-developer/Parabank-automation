# ParaBank Test Automation Framework

UI test automation framework for the [ParaBank](https://parabank.parasoft.com) demo banking application, built with **Java, Selenium WebDriver, TestNG and the Page Object Model**. Tests run locally or in GitHub Actions, and every CI run publishes an ExtentReports HTML report that is hosted on Render.

**Live test report:** https://YOUR-SITE-NAME.onrender.com  <!-- replace after Render setup -->

## What is tested

| Area | Test | Type |
|---|---|---|
| Register | Register a new user with a unique username | Positive |
| Login | Valid credentials open Accounts Overview | Positive |
| Login | Wrong password, empty fields (TestNG `@DataProvider`) | Negative |
| Accounts Overview | Overview table lists the user's accounts after login | Positive |
| Transfer Funds | Transfer $50 between the user's own accounts | Positive |
| Transfer Funds | Empty / non-numeric amount (`@DataProvider`) | Negative |
| Bill Pay | Pay a bill to a payee | Positive |
| Bill Pay | Mismatched account numbers | Negative |

Every test registers its own fresh user, so tests do not depend on each other or on pre-existing data.

## Tech stack

- Java 17, Maven
- Selenium WebDriver 4 (Selenium Manager downloads browser drivers automatically)
- TestNG (test runner, assertions, `@DataProvider`)
- ExtentReports 5 (HTML report with screenshots on failure)
- Page Object Model
- GitHub Actions (CI) and Render Static Site (report hosting)

## Folder structure

```
parabank-automation/
├── .github/workflows/ci.yml        # CI: run tests headless, commit docs/ back to the repo
├── docs/index.html                 # Generated Extent report (hosted on Render)
├── pom.xml                         # Dependencies and plugins
└── src/
    ├── main/java/
    │   ├── base/
    │   │   ├── DriverFactory.java  # Creates / quits the WebDriver (chrome, firefox, edge, headless)
    │   │   └── BaseTest.java       # @BeforeMethod opens browser, @AfterMethod closes it, registerNewUser()
    │   ├── pages/
    │   │   ├── BasePage.java       # Shared explicit-wait helpers (click, type, getText, isDisplayed)
    │   │   ├── LoginPage.java
    │   │   ├── RegisterPage.java
    │   │   ├── AccountsOverviewPage.java   # Overview table + left menu navigation
    │   │   ├── OpenNewAccountPage.java     # Opens a 2nd account for the transfer test
    │   │   ├── TransferFundsPage.java
    │   │   └── BillPayPage.java
    │   ├── utils/
    │   │   ├── ConfigReader.java   # Reads config.properties (overridable with -Dkey=value)
    │   │   ├── ScreenshotUtil.java # Takes a Base64 screenshot
    │   │   └── ReportManager.java  # Creates the ExtentReports instance, writes docs/index.html
    │   └── listeners/
    │       └── TestListener.java   # TestNG listener: logs results, screenshot on failure
    └── test/
        ├── java/tests/
        │   ├── RegisterTest.java
        │   ├── LoginTest.java
        │   ├── TransferFundsTest.java
        │   └── BillPayTest.java
        └── resources/
            ├── config.properties   # baseUrl, browser, headless, timeout
            └── testng.xml          # Suite: listener + test classes
```

## Framework rules

- Page classes contain **locators and actions only**; all assertions are in test classes (TestNG `Assert`).
- Only **explicit waits** (`WebDriverWait`) are used, never `Thread.sleep` or implicit waits.
- Locators use **id, name and CSS** selectors (no XPath).
- A new browser is started for every test, so tests are independent.

## Run locally

Prerequisites: Java 17, Maven 3.9+, Google Chrome.

```bash
# Run the whole suite (Chrome window opens)
mvn clean test

# Run headless
mvn clean test -Dheadless=true

# Run in another browser
mvn clean test -Dbrowser=firefox

# Run one test class (note: -Dtest bypasses testng.xml, so no Extent report is written)
mvn clean test -Dtest=LoginTest
```

Then open `docs/index.html` in a browser to see the report.

Settings live in `src/test/resources/config.properties`. Any value can be overridden from the command line with `-Dkey=value`.

## Run in CI (GitHub Actions)

The workflow in `.github/workflows/ci.yml` runs on every push to `main` (and manually from the **Actions** tab via *Run workflow*). It:

1. Checks out the code and installs Java 17 (with Maven dependency caching).
2. Runs `mvn clean test -Dheadless=true` in headless Chrome.
3. Commits the updated `docs/` folder back to the repository, even if tests failed.

Render notices that commit and redeploys the report automatically.

## Host the report on Render

1. Push this repository to GitHub.
2. Go to [render.com](https://render.com), sign in with GitHub, click **New + → Static Site**.
3. Select this repository and fill in:
   - **Branch:** `main`
   - **Root Directory:** leave empty
   - **Build Command:** leave empty
   - **Publish Directory:** `docs`
4. Click **Create Static Site**. When the deploy finishes, copy the `https://<name>.onrender.com` URL into the *Live test report* link at the top of this README.

Auto-deploy is on by default, so each report commit from CI updates the live site.
