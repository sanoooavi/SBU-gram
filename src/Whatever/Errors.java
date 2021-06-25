package Whatever;

import Client.ClientManager;
import javafx.scene.control.Alert;

import java.util.regex.Pattern;

public class Errors {
    /**
     * this class has all the error to know if the command is true or not
     * all the methods are static so that you can call them whenever you want from any class
     * and to shorten the line by not calling same thing more than twice
     */
    public static void ShowInvalidViewProfileDialog() {
        String title = "Error in ViewProfile";
        String contentText = "This user might have deleted account";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowInvalidRePostDialog() {
        String title = "Error in RePosting";
        String contentText = "You Can Not repost this post!";
        makeAndShowInformationDialog(title, contentText);
    }


    public static void ShowInvalidLikeDialog() {
        String title = "Error in Liking";
        String contentText = "You must have liked this post before\nOr this user has deleted account";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowInvalidCommentDialog() {
        String title = "Error in adding comment";
        String contentText = "The comment part is null\nOr this user has deleted account";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowSuccessfulRepostDialog() {
        String title = "Successful Reposting";
        String contentText = "You Have reposted";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ChangedPasswordComplete() {
        String title = "Success";
        String contentText = "You have changed your password";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void CanNotChangePassword() {
        String title = "Error";
        String contentText = "The old password wasn't correct\nPlease try again";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowHasSuccessfullymade() {
        String title = "Success";
        String contentText = "Password saved successfully!";
        makeAndShowInformationDialog(title, contentText);
    }


    public static void ShowInvalidFilling() {
        String title = "Error";
        String contentText = "Please Fill in the required fields";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowNotHasTheSecPassword() {
        String title = "Warning";
        String contentText = "There is no such name \nor you may have entered the wrong password";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void makeSuccessfulDialog() {
        String title = "Successful";
        String contentText = "You have posted a new post";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void showFillRequiredFieldsDialog() {
        String title = "Incomplete information";
        String contentText = "Please fill all of the required fields";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ErrorSignUpEmptyFields() {
        String title = "Error in SignUp";
        String contentText = "Please Fill in the required fields";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void showProfileCreatedDialog() {
        String title = "Success";
        String contentText = "profile created successfully!";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowInvalidChooseGenderDialog() {
        String title = "Wrong choose";
        String contentText = "You can not be both a man and a woman:|!";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowInvalidUserDialog() {
        String title = "wrong Input";
        String contentText = "There is no such user with this name";
        makeAndShowInformationDialog(title, contentText);
    }


    public static boolean isValidUsername(String username) {
        boolean exists = ClientManager.isUserNameExists(username);
        if (exists) {
            String title = "Failed to create profile";
            String contentText = "Username already exists, choose another one!";
            makeAndShowInformationDialog(title, contentText);
        }
        return !exists;
    }

    public static void ShowBlockedDialog() {
        String title = "Oops";
        String contentText = "You can not see this user's profile \nYou are blocked";
        makeAndShowInformationDialog(title, contentText);
    }

    public static boolean isValidPassword(String text, String text2) {
        if (!text.equals(text2)) {
            String title = "invalid password";
            String contentText = "The passwords are not the same \nPlease try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        String regex = "^[a-zA-Z0-9]{8,}";
        if (!Pattern.matches(regex, text)) {
            String title = "invalid password";
            String contentText = "The password is at least 8 letters long and includes numbers and letters Please try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    public static boolean isValidBirth(int year) {
        if ((2021 - (year)) < 7) {
            String title = "Age warning";
            String contentText = "you should be at least 7\nplease wait few years :D";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        if (email.isEmpty())
            return true;
        String regexEmail = "^(.+)@(.+)$";
        if (!Pattern.matches(regexEmail, email)) {
            String title = "invalid email-address";
            String contentText = "Please use valid emails\n then try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "09([0-9]{9})";
        if (phoneNumber.isEmpty())
            return true;
        if (!Pattern.matches(regex, phoneNumber)) {
            String title = "invalid phoneNumber";
            String contentText = "Please enter valid number\n then try again";
            makeAndShowInformationDialog(title, contentText);
            return false;
        }
        return true;
    }

    public static void ShowInvalidFollowDialog() {
        String title = "Blocked";
        String contentText = "You can not follow this user\n You are blocked!";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void InvalidLoginDialog() {
        String title = "Incomplete information";
        String contentText = "Please fill all of the required fields";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void ShowInvalidLoginDialog() {
        String title = "Error in login";
        String contentText = "invalid username or password\nTry again or sign up";
        makeAndShowInformationDialog(title, contentText);
    }

    public static void makeAndShowInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
