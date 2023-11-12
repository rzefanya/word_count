# Count Words

Application for counting and extracting words in from texts

# Features
## Manage Rules
By default, count word already have rule to extract words starting with m longer than 5 characters.
This can easily replaced by other rules by saving new rule with name "default".
It's also possible to add more rules with different name.

## Manage Text
By default, this application already have 1 text for processing.
With this feature, user can add more text.

## Count words
Count words will execute the rule to process text.
User can either :
- run default rule to process default text
- run default rule to process user uploaded text
- run saved rule to process saved text

# Improvements
Count Words is highly extensible application. 
Developer may add new processor by adding new CountingRuleRequest, extending BaseProcessor and register it in ProcessorServiceImpl.

# Running the Application
Simply run using maven spring-boot:run or run the Application.java directly and open http://localhost:8080/swagger-ui/index.html afterwards