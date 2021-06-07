# PasswordStrengthView
This is an android library to represent password strength.


# Preview
![image](https://user-images.githubusercontent.com/46835920/120934020-470c5600-c71a-11eb-8499-4394d79fde9c.png)
![image](https://user-images.githubusercontent.com/46835920/120933956-0e6c7c80-c71a-11eb-954a-127234e45ad1.png)
![image](https://user-images.githubusercontent.com/46835920/120933965-162c2100-c71a-11eb-87f9-9cbe6cd1b66e.png)
![image](https://user-images.githubusercontent.com/46835920/120933973-1debc580-c71a-11eb-96f5-ba140baa83b1.png)
![image](https://user-images.githubusercontent.com/46835920/120933994-2d6b0e80-c71a-11eb-8890-0e161f588e5b.png)
![image](https://user-images.githubusercontent.com/46835920/120934005-352ab300-c71a-11eb-89f7-d214a0c00374.png)


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
	implementation 'com.github.RHSaliya:PasswordStrengthView:1.0'
```


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
passwordSV.attachEditText(keyET);
```
or use update method

```java
passwordSV.update(String password);
```
