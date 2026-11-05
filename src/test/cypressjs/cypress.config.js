module.exports = {
  defaultCommandTimeout: 10000,
  reporter: 'junit',
  chromeWebSecurity: false,
  reporterOptions: {
    mochaFile: 'results/cypressTest-[hash].xml',
  },
  env: {
    default_website_url: 'https://staging-openlibertyio.mqj6zf7jocq.us-south.codeengine.appdomain.cloud',
    // Backend (REST API) host used by the v1 API tests, which call /api/start directly instead of using the UI.
    // Taken from CONTRIBUTING.md - update it if the staging backend URL changes.
    default_api_url: 'https://starter-staging.rh9j6zz75er.us-east.codeengine.appdomain.cloud',
    default_jdk: '17',
  },
  viewportWidth: 1280,
  e2e: {
    supportFile: '/__w/start.openliberty.io/start.openliberty.io/src/test/cypressjs/cypress/support/e2e.js',
    specPattern: '/__w/start.openliberty.io/start.openliberty.io/src/test/cypressjs/cypress/e2e/*.cy.js',
    },
}