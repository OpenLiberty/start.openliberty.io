module.exports = {
  defaultCommandTimeout: 10000,
  reporter: 'junit',
  chromeWebSecurity: false,
  reporterOptions: {
    mochaFile: 'results/cypressTest-[hash].xml',
  },
  env: {
    default_website_url: 'https://staging-openlibertyio.mqj6zf7jocq.us-south.codeengine.appdomain.cloud',
    default_jdk: '11',
  },
  viewportWidth: 1280,
  e2e: {
    supportFile: '/__w/github-projects/github-projects/src/test/cypressjs/cypress/support/e2e.js',
    specPattern: '/__w/github-projects/github-projects/src/test/cypressjs/cypress/e2e/*.cy.js',
    },
}