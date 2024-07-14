# CaptchaBreaker

#### Background story:
I saw a YouTubeShorts video about reCHAPTCA. The main point was, that if you move your mouse right to the checkbox and click it, you cant verify yourself. But if it is not moving on a straight line (like a human moves a mouse) the reCHAPTCHA (surprise, surprise...) recognizes you as a human and the verification is successful. So I tought lets just try it!

#### General:
This application is based on Java, Selenium and Spring.
I am using Googles offical website for testing https://www.google.com/recaptcha/api2/demo

#### Usage:

Clone the repository in some code editor (eg Eclipse or VSCode), which is able to run a java application.
Uncomment the method (or all) which you want to try. Start the application and just watch it working...

#### Requirements:
You have to have GoogleChrome and maven installed on your machine

## Progress until now
#### Method 1
Simply try to modify the aria-checked attribute of the checkbox.

Of course (and fortunately) it does not work like this

#### Method 2
Simply click the checkbox

As I mentioned before (based on the YouTube video) this still not breaks it. After submitting the form, got another check with pictures to find the bycicles... 

#### Method 3
Modify all the needed attributes of the checkbox.
I checked the element (with Firefox DevTools) befor and after the click, and modified all the needed attributes (eg classes, tabindex) with the help of a JavascriptExecutor.

Checking the checkbox was successful but cant submit the form. Dont even a single find-the-bicycles-test...<br/>
Of course not just the attributes of that single element was changed, but there is a loading period before the final state (whatt should be also simulated) and the whole DOM structure changes. So if someone have time... its not absolutely hopeless

#### Method 4
Click the checkbox, but hover on some other elements first (simulating humans movements)

Just another find-the-bycicles-test -> a better human simulation is needed

#### Method 5 (work in progress)
Getting the data points of a human mouse movement (planned with Firefox DevTools. Im courious...) and use them for hovering the pixels of the way to the checkbox.

Results coming soon...

#### (Hopefully not needed) Method 6
...




