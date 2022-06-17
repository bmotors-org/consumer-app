package bm.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import bm.app.data.serde.OtpVerificationResponse
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
    otpVerification: suspend (String, String) -> OtpVerificationResponse,
    saveToStorage: suspend (String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val (waiting, setWaiting) = rememberSaveable {
        mutableStateOf(false)
    }

    val (successAlpha, setSuccessAlpha) = rememberSaveable {
        mutableStateOf(SuccessAlphaAnim.Start)
    }

    val (errorAlpha, setErrorAlpha) = rememberSaveable {
        mutableStateOf(ErrorAlphaAnim.Start)
    }

    // Variables for the animation
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
            setWaiting(false)
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
            errorState.targetState = false
            setWaiting(false)
        }
    )

    // If cicular progress bar is disappearing, trigger
    // the animation to show the success icon, or error icon
    // depending on the verification result
    if (!progressState.isIdle && progressState.currentState) {
        if (verified) {
            successState.targetState = true
            setSuccessAlpha(SuccessAlphaAnim.End)
        } else {
            errorState.targetState = true
            setErrorAlpha(ErrorAlphaAnim.End)
        }
    }

    AlertDialog(
        onDismissRequest = { setOtpInputDialogVisibility(false) },
        confirmButton = {
            Button(
                enabled = !waiting,
                onClick = {
                    setWaiting(true)
                    progressState.targetState = true
                    coroutineScope.launch(Dispatchers.Default) {
                        val result = otpVerification(phoneNumber, otpCode)
                        if (result.success) {
                            println("Success Success Success")
                            // saveToStorage(phoneNumber, result.token)
                            setVerified(true)
                        }
                        progressState.targetState = false
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
                enabled = !waiting,
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
            if (!waiting) {
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
