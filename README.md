# PasswordStrengthView
This is my first shot at creating a library in the Android SDK. This is an Android library to represent password strength, or you can use it to measure password strength yourself.

# Preview
The images below show how you can adjust the view to achieve different shapes.

<img width="194" alt="Empty" src="https://user-images.githubusercontent.com/46835920/125243912-8989fb00-e2a3-11eb-90af-73b6282686ce.png"> <img width="194" alt="Ok" src="https://user-images.githubusercontent.com/46835920/125243918-8abb2800-e2a3-11eb-9819-b3d7065b0a10.png"> <img width="194" alt="Easy" src="https://user-images.githubusercontent.com/46835920/125243907-8858ce00-e2a3-11eb-8ee0-5eea043b4394.png">

<img width="194" alt="Medium" src="https://user-images.githubusercontent.com/46835920/125243915-8a229180-e2a3-11eb-87c2-838b0875badb.png"> <img width="194" alt="Strong" src="https://user-images.githubusercontent.com/46835920/125243920-8b53be80-e2a3-11eb-889c-80ecfa7a43a3.png"> <img width="194" alt="VStrong" src="https://user-images.githubusercontent.com/46835920/125243925-8bec5500-e2a3-11eb-96aa-27f7e7a26779.png">



# How to use?

Add maven to your project gradle file
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add  dependency in your app gradle file.
```java
implementation 'com.github.RHSaliya:PasswordStrengthView:1.5'
```

Add view on your layout
```xml
<com.rhs.psw.PasswordStrengthView
        android:id="@+id/passwordSV"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="24sp"
        app:indicatorWidth="16dp"
        app:empty_color="#ddd"/>
```

Attatch EditText directly
```java
passwordSV.attachEditText(passwordET);
```
or use update method

```java
passwordSV.update(String password);
```

To just calculate strength
```java
Calculator calculator = new Calculator();
calculator.initScores(10,20,30,40);
calculator.calculate("Password",Calculator.INCREMENTAL);
```
