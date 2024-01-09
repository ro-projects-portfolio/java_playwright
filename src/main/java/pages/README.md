## To implement POM in Playwright we need to create a Page variable and constructor
### example:
##### Variable: 
    private final Page variableName(same as a Class);
##### Constructor: 
    public constructorName(Page page) {
        this.variable = page;
    }
### WebElement related to this page determine as a private static final String (constants) where value it is selector
    private static final String WEBELEMENT_NAME = "selector";