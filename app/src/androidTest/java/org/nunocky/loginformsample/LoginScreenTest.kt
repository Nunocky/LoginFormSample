package org.nunocky.loginformsample

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.nunocky.loginformsample.BuildConfig.TEST_PASSWORD
import org.nunocky.loginformsample.BuildConfig.TEST_USERNAME
import org.nunocky.loginformsample.login.LoginScreen

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoginSuccess() {
        val validUsername = TEST_USERNAME
        val validPassword = TEST_PASSWORD

        composeTestRule.setContent {
            LoginScreen()
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE))

        composeTestRule.onNodeWithTag(TAG_USERNAME).performTextInput(validUsername)
        composeTestRule.onNodeWithTag(TAG_PASSWORD).performTextInput(validPassword)
        composeTestRule.onNodeWithTag(TAG_BTN_LOGIN).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE_LOADING))

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag(TAG_TEXT_MESAGE_SUCCESS),
            timeoutMillis = 5000
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoginFail() {
        val invalidUserName = "xyz"
        val invalidPassword = "xyz987"

        composeTestRule.setContent {
            LoginScreen()
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE))

        composeTestRule.onNodeWithTag(TAG_USERNAME).performTextInput(invalidUserName)
        composeTestRule.onNodeWithTag(TAG_PASSWORD).performTextInput(invalidPassword)
        composeTestRule.onNodeWithTag(TAG_BTN_LOGIN).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE_LOADING))

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag(TAG_TEXT_MESAGE_ERROR),
            timeoutMillis = 5000
        )
    }
}