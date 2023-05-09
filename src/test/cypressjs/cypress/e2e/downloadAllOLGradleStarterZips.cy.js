describe('Test Open Liberty Starter - Gradle', () => {
   
    var starterURL = Cypress.env('default_website_url');
    
    it('Test zip files are downloaded for gradle', () => {
        cy.log(`starterURL =  ` + starterURL);
        cy.visit(starterURL + '/start/');
        cy.downloadZipFiles('g');
    });

});