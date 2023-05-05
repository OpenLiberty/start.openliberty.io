// Common methods called from the scripts to select the java, jakarta(ee) and microprofile levels
// and dowload the created zip files for all of them 
// It will build either the gradle or maven version of the zipfile based on the passed in
// parameter to downloadZipFiles

// NOTE - Right now, all possible combinations are allowed by the ui so we go ahead and create the
// invalid zip files for java 8 with EE 10 or mp 6 but the github actions that will try to 
// test the zips files skip these - when the ui is fixed to prevent those combinations, this
// code will need to change



// used for file naming
const convertNum2Str = {
        '8': '8',
        '11': '11',
        '17': '17',
        '10': '10',
        '9.1': '91',
        '8.0': '8',
        '7.0': '7',
        '6.0': '6',
        '5.0': '5',
        '4.1': '41',
        '3.3': '33',
        '2.2': '22',
        '1.4': '14',
        None: 'None'
};

// must define all possible valid combinations of jakarta and mp
// keep in mind not all java versions work with all of these for ex. ee10, mp6 cannot use java 8

let jakarta_mp_versions = [
        {
          jakarta: "10",
          mp: "6.0"
        },
        {
          jakarta: "10",
          mp: "None"
        },
        {
          jakarta: "9.1",
          mp: "5.0"
        },
        {
          jakarta: "9.1",
          mp: "None"
        },
        {
          jakarta: "8.0",
          mp: "4.1"
        },
        {
          jakarta: "8.0",
          mp: "3.3"
        },
        {
          jakarta: "8.0",
          mp: "2.2"
        },
        {
          jakarta: "8.0",
          mp: "None"
        },
        {
          jakarta: "7.0",
          mp: "1.4"
        },
        {
          jakarta: "7.0",
          mp: "None"
        },
        {
          jakarta: "None",
          mp: "6.0"
        },
        {
          jakarta: "None",
          mp: "5.0"
        },
        {
          jakarta: "None",
          mp: "4.1"
        },
        {
          jakarta: "None",
          mp: "3.3"
        },
        {
          jakarta: "None",
          mp: "2.2"
        },
        {
          jakarta: "None",
          mp: "1.4"
        }
    ]

// Loop through all possible combinations of jakarta and microprofile to create the starter zip files
// gOrM - values are g or m for gradle or maven

Cypress.Commands.add('downloadZipFiles',(gOrM) => {
    for (let i = 0; i < jakarta_mp_versions.length; i++) {
            const version = jakarta_mp_versions[i];
            const jktVer = version.jakarta;
            cy.log(`jakarta version ` + jktVer);
            const mpVer = version.mp;
            cy.log(`mp version ` + mpVer);
          
            const appname = 'appzip-jdk' + convertNum2Str[Cypress.env('JDK_VERSION')] + '-ee' + convertNum2Str[jktVer] + '-mp' + convertNum2Str[mpVer];
            cy.log(`appname ` + appname);
            cy.downloadZipFile(appname, jktVer, mpVer, gOrM); 
    }
});


// Method that actually interacts with the website to download the zip
// appname - for building the downloaded filename  appzip-jdk?-ee?-mp?.zip
// jktVer - which jakarta version to pick
// mpVer - which microprofile version to pick
// gOrM - gradle or maven, g or m

Cypress.Commands.add('downloadZipFile', (appname, jktVer, mpVer, gOrM) => {
    cy.log('appname ' + appname);
    var jdkVer = Cypress.env('JDK_VERSION');
    cy.log('jdkVer ' + jdkVer);
    const downloadsFolder = Cypress.config('downloadsFolder');
    const path = require("path");

    // select gradle or maven
    if (gOrM == 'g') {
        cy.get('#build_system_gradle',{ timeout:10000 }).click();
    } else {
        cy.get('#build_system_maven',{ timeout:10000 }).click(); 
    }

    // select jdk version, jakarta version, mp version
    cy.get('#Starter_Java_Version',{ timeout:10000 }).select(convertNum2Str[Cypress.env('JDK_VERSION')]);
 
    if (jktVer) {
        cy.log('jktVer ' + jktVer);
        cy.get('#Starter_Jakarta_Version').select(jktVer);
    }
    if (mpVer) {
        cy.log('mpVer ' + mpVer);
        cy.get('#Starter_MicroProfile_Version').select(mpVer);
    }
    cy.get("#starter_submit").click({force: true});

    // for some unknown reason have to add the wait along with click force true to close the modal
    cy.wait(10000);
    cy.get('.modal-dialog',{ timeout:10000 }).should('be.visible');
    if (gOrM == 'g') {    
         cy.get('#cmd_to_run').contains('gradlew libertyDev');
    } else {
            cy.get('#cmd_to_run').contains('mvnw liberty:dev');
    }        
    cy.get('#gen_proj_popup_button').click({force: true}).then(() => {
       // need to make this synchronous because it can do the move and keep going in the loop before
       // the click for the popup happens
            
       cy.readFile(path.join(downloadsFolder, `app-name.zip`)).should("exist");
    
       // rename app-name.zip to ${appname}.zip
       cy.log(`mv app-name.zip to ${downloadsFolder}/${appname}`);
       cy.exec(`mv ${downloadsFolder}/app-name.zip ${downloadsFolder}/${appname}.zip`).its('stderr').should('be.empty'); 
    });  

});