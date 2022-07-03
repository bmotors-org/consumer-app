package bm.app.screens.service.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import bm.app.R
import bm.app.screens.service.api.network.data.OtpVerificationRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class SuccessAlphaAnim {
    Start,
    End
}

enum class ErrorAlphaAnim {
    Start,
    End
}

@Composable
fun OtpInputDialog(
    phoneNumber: String,
    otpCode: String,
    verified: Boolean,
    setOtpCode: (String) -> Unit,
    setOtpInputDialogVisibility: (Boolean) -> Unit,
    setVerified: (Boolean) -> Unit,
    verifyOtp: suspend (String, String) -> OtpVerificationRes,
    storeCreds: suspend (String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val (successAlpha, setSuccessAlpha) = rememberSaveable {
        mutableStateOf(SuccessAlphaAnim.Start)
    }

    val (errorAlpha, setErrorAlpha) = rememberSaveable {
        mutableStateOf(ErrorAlphaAnim.Start)
    }

    // Variables for the animation
    val idleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    val progressState = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }

    val successState = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }

    val errorState = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }

    // Alpha animation
    val successAlphaAnim by animateFloatAsState(
        targetValue = when (successAlpha) {
            SuccessAlphaAnim.Start -> 0f
            SuccessAlphaAnim.End -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow,
            visibilityThreshold = 0.1f
        ),
        finishedListener = {
            setOtpInputDialogVisibility(false)
            // Reset the animation
            successState.targetState = false
        }
    )

    val errorAlphaAnim by animateFloatAsState(
        targetValue = when (errorAlpha) {
            ErrorAlphaAnim.Start -> 0f
            ErrorAlphaAnim.End -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow,
            visibilityThreshold = 0.1f
        ),
        finishedListener = {
            // If error icon is visible, make it invisible
            if (errorState.targetState) {
                errorState.targetState = false
            }
        }
    )

    // If textfield is disappearing
    if (!idleState.isIdle && idleState.currentState) {
        progressState.targetState = true // triggers progress animation
    }

    // When the error icon is disappearing
    if (!errorState.isIdle && errorState.currentState) {
        idleState.targetState = true // show textfield
        setErrorAlpha(ErrorAlphaAnim.Start) // reset alpha state
    }

    // When the success icon is disappearing
    if (!successState.isIdle && successState.currentState) {
        setSuccessAlpha(SuccessAlphaAnim.Start) // reset alpha state
    }

    // If cicular progress bar is disappearing, trigger
    // the animation to show the success icon, or error icon
    // depending on the verification result
    if (!progressState.isIdle && progressState.currentState) {
        if (verified) {
            // trigger the success icon visibility
            successState.targetState = true
            // trigger the success alpha animation
            setSuccessAlpha(SuccessAlphaAnim.End)
        } else {
            // trigger the error icon visibility
            errorState.targetState = true
            // trigger the error alpha animation
            setErrorAlpha(ErrorAlphaAnim.End)
        }
    }

    AlertDialog(
        onDismissRequest = { setOtpInputDialogVisibility(false) },
        confirmButton = {
            Button(
                enabled = idleState.targetState,
                onClick = {
                    idleState.targetState = false // hides the textfield
                    coroutineScope.launch(Dispatchers.Default) {
                        val result = verifyOtp(phoneNumber, otpCode)
                        if (result.success) {
                            setVerified(true)
                            storeCreds(phoneNumber, result.sessionID!!)
                        }
                        progressState.targetState = false // stops progress animation
                    }
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Verify"
                )
            }
        },

        dismissButton = {
            TextButton(
                enabled = idleState.targetState,
                onClick = {
                    setOtpInputDialogVisibility(false)
                    setOtpCode("")
                },
                contentPadding = PaddingValues(16.dp, 10.dp)
            ) {
                Text(
                    text = "Cancel"
                )
            }
        },

        icon = {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(32.dp)
            )
        },

        title = {
            Text(
                text = "Enter the otp sent to your phone number",
                textAlign = TextAlign.Center
            )
        },

        text = {
            AnimatedVisibility(
                visibleState = idleState,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                OutlinedTextField(
                    value = otpCode,
                    onValueChange = {
                        if (it.length <= 4) {
                            setOtpCode(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    ),
                    label = {
                        Text(
                            text = "OTP"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            AnimatedVisibility(
                visibleState = progressState,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            AnimatedVisibility(
                visibleState = successState
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(successAlphaAnim)
                        .fillMaxWidth()
                        .size(200.dp)
                )
            }

            AnimatedVisibility(visibleState = errorState) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(errorAlphaAnim)
                        .fillMaxWidth()
                        .size(200.dp)
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        iconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    )
}
