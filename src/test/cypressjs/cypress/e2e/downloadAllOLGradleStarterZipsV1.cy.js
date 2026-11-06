describe('Test Open Liberty Starter - Gradle - v1 API', () => {

    var apiURL = Cypress.env('default_api_url');

    it('Test zip files are downloaded for gradle using the v1 API', () => {
        cy.log(`apiURL =  ` + apiURL);
        cy.downloadZipFilesV1('g');
    });

    it('Test the v1 API rejects Java 25 (only supported by the v2 API)', () => {
        cy.request({
            url: apiURL + '/api/start',
            qs: { a: 'app-name', g: 'com.demo', j: '25', b: 'gradle', e: '10.0', m: '6.1' },
            failOnStatusCode: false
        }).its('status').should('eq', 400);
    });

});
