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

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

//    @get:Rule
//    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testShouldSuccess() {
        composeTestRule.setContent {
            LoginScreen()
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE))

        // input username and password
        composeTestRule.onNodeWithTag(TAG_USERNAME).performTextInput("abc")
        composeTestRule.onNodeWithTag(TAG_PASSWORD).performTextInput("abc123")
        composeTestRule.onNodeWithTag(TAG_BTN_LOGIN).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE_LOADING))

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag(TAG_TEXT_MESAGE_SUCCESS),
            timeoutMillis = 5000
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testShouldFail() {
        composeTestRule.setContent {
            LoginScreen()
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE))

        composeTestRule.onNodeWithTag(TAG_USERNAME).performTextInput("xyz")
        composeTestRule.onNodeWithTag(TAG_PASSWORD).performTextInput("xyz123")
        composeTestRule.onNodeWithTag(TAG_BTN_LOGIN).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TAG_TEXT_MESAGE_LOADING))

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag(TAG_TEXT_MESAGE_ERROR),
            timeoutMillis = 5000
        )
    }
}