package com.example.appnewkool.ui.login.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appnewkool.R
import com.example.appnewkool.data.model.Account
import com.example.appnewkool.ui.theme.AppNewkoolTheme
import com.example.appnewkool.ui.theme.BlueWhite
import com.example.appnewkool.ui.theme.WhiteBlue
import com.example.appnewkool.ui.theme.WhiteBlue2

@Composable
fun SignInScreen(
    viewModel: SignInViewModel? = hiltViewModel(),
    onNavToHome: () -> Unit
) {

    val inputUserState = viewModel?.inputUserState ?: InputUserState()

    if(viewModel?.signInResult?.token?.token != null){
        LaunchedEffect(viewModel.signInResult?.token?.token) {
            Log.e("sign in", "SignInScreen: " + viewModel.signInResult?.token?.token)
            onNavToHome.invoke()
        }
    }

    if(viewModel?.toast != null){
        val context = LocalContext.current
        LaunchedEffect(viewModel?.toast ){
            Toast.makeText(context, viewModel?.toast, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Blue, Color.Black)
                )
            )
            .padding(10.dp)
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "Login", color = Color.White, fontSize = 70.sp,
                    fontFamily = FontFamily(Font(R.font.modulusbold)),
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.weight(1f))

                EmailInput(email = inputUserState.email, error = inputUserState.emailErrorMessage) {
                    viewModel?.onEmailInputChange(it)
                }
                Spacer(modifier = Modifier.height(30.dp))
                inputUserState.showPassword?.let {
                    PasswordInput(inputUserState.passWord, inputUserState.passwWordErrorMessage,
                        it, {
                            val image = if (inputUserState.showPassword == true)
                                R.drawable.ic_baseline_visibility_24 else R.drawable.ic_baseline_visibility_off_24
                            val description =
                                if (inputUserState.showPassword == true) "Hide password" else "Show password"
                            IconButton(onClick = {
                                viewModel?.onClickShowPass()
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = image),
                                    description
                                )
                            }
                        }) {
                        viewModel?.onPasswordInputChange(it)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Haven't Account?", color = Color.White,
                        fontFamily = FontFamily(Font(R.font.modulusmedium))
                    )

                    Text(
                        modifier = Modifier.clickable(onClick = {
                            onNavToHome.invoke()
                        }),
                        text = "Skip",
                        color = WhiteBlue2,
                        fontFamily = FontFamily(Font(R.font.modulusbold)),
                        fontWeight = FontWeight.Black,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                BtnSignIn(
                    inputUserState.email,
                    inputUserState.passWord,
                    enable = {
                        inputUserState.email != "" && inputUserState.passWord != "" &&
                                inputUserState.emailErrorMessage == null &&
                                inputUserState.passwWordErrorMessage == null
                    },
                ) {
                    viewModel?.signIn(it)
                }


                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
        if(viewModel?.isLoading == true){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
                    .clickable { false },
                contentAlignment = Alignment.BottomCenter
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}



@Composable
fun BtnSignIn(
    email: String,
    password: String,
    enable: () -> Boolean,
    onClick: (Account) -> Unit,
) {
    Button(
        onClick = {
            if (email != "" && password != "") {
                onClick(Account(email, password))
            }
        }, shape = CircleShape, modifier = Modifier.size(70.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = WhiteBlue2,
            disabledBackgroundColor = BlueWhite
        ),
        enabled = enable.invoke()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
            "",
            tint = Color.Black,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun EmailInput(email: String, error: String?, onEmailChange: (String) -> Unit) {
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                backgroundColor = WhiteBlue,
                textColor = Color.White,
                cursorColor = Color.White,
                errorCursorColor = MaterialTheme.colors.error,
                unfocusedLabelColor = Color.White,
                errorLabelColor = MaterialTheme.colors.error,
                focusedLabelColor = Color.White,
                leadingIconColor = Color.White,
                errorLeadingIconColor = MaterialTheme.colors.error
            ),
            shape = RoundedCornerShape(40.dp),
            leadingIcon = {
                Row() {
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.leading_username),
                        contentDescription = "iconEmail",
                        modifier = Modifier.size(30.dp),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = error != null,
            label = {
                Text(
                    text = stringResource(id = R.string.email_hint),
                )
            },
            singleLine = true
        )
        error?.let { IsErrorField(it) }
    }

}

@Composable
fun PasswordInput(
    password: String,
    error: String?,
    showPass: Boolean,
    onShowPassword: @Composable (() -> Unit),
    onPasswordChange: (String) -> Unit
) {
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                backgroundColor = WhiteBlue,
                textColor = Color.White,
                cursorColor = Color.White,
                errorCursorColor = MaterialTheme.colors.error,
                unfocusedLabelColor = Color.White,
                errorLabelColor = MaterialTheme.colors.error,
                focusedLabelColor = Color.White,
                leadingIconColor = Color.White,
                errorLeadingIconColor = MaterialTheme.colors.error
            ),
            shape = RoundedCornerShape(40.dp),
            leadingIcon = {
                Row() {
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "iconEmail",
                        modifier = Modifier.size(30.dp),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = error != null,
            label = {
                Text(
                    text = stringResource(id = R.string.password_hint),
                )
            },
            visualTransformation = if (!showPass) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            trailingIcon = onShowPassword
        )
        error?.let { IsErrorField(error = it) }
    }

}

@Composable
fun IsErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        color = MaterialTheme.colors.error
    )
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    AppNewkoolTheme() {
        SignInScreen(null) {
        }
    }
}

