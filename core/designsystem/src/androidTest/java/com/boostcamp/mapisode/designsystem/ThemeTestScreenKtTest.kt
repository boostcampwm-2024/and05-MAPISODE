package com.boostcamp.mapisode.designsystem

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemeTestScreenKtTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@Test
	fun testScreen() {
		composeTestRule.setContent {
			MapisodeTheme {
				TestScreen()
			}
		}

		// TopAppBar 확인
		composeTestRule.onNodeWithTag("TopAppBar")
			.assertIsDisplayed()

		// BottomAppBar 확인
		composeTestRule.onNodeWithTag("BottomAppBar")
			.assertIsDisplayed()

		// Button1 확인
		composeTestRule.onNodeWithTag("Button1")
			.assertIsDisplayed()
			.assertHasClickAction()

		// Button2 확인
		composeTestRule.onNodeWithTag("Button2")
			.assertIsDisplayed()
			.assertHasClickAction()

		// 텍스트 확인
		composeTestRule.onNodeWithText("디플 라지").assertIsDisplayed()
		composeTestRule.onNodeWithText("디플 미디엄").assertIsDisplayed()
		composeTestRule.onNodeWithText("디플 스몰").assertIsDisplayed()

		composeTestRule.onNodeWithText("헤드 라지").assertIsDisplayed()
		composeTestRule.onNodeWithText("헤드 미디엄").assertIsDisplayed()
		composeTestRule.onNodeWithText("헤드 스몰").assertIsDisplayed()

		composeTestRule.onNodeWithText("바디 라지").assertIsDisplayed()
		composeTestRule.onNodeWithText("바디 미디엄").assertIsDisplayed()
		composeTestRule.onNodeWithText("바디 스몰").assertIsDisplayed()
	}
}
