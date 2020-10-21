package io.openliberty.website.starter.metadata.nls.resources;

public class Messages extends java.util.ListResourceBundle
{
   public Object[][] getContents() {
       return resources;
   }
   private final static Object[][] resources= {
      { "appName", "App Name" },
      { "basePackage", "Base Package"},
      { "buildSystem", "Build System"},
      { "jakartaEEVersion", "Java EE / Jakarta EE Version"},
      { "javaVersion", "Java SE Version"},
      { "microProfileVersion", "MicroProfile Version"},
      { "templateProcessError", "Failed to process template"},
      { "jakartaEEValidationMessage", "Jakarta EE version requested was ${validatedValue} but must be one of: {permittedValues}"},
      { "javaValidationMessage", "Java version requested was ${validatedValue} but must be one of: {permittedValues}"},
      { "microprofileValidationMessage", "Eclipse MicroProfile version requested was ${validatedValue} but must be one of: {permittedValues}"}
   };
}
