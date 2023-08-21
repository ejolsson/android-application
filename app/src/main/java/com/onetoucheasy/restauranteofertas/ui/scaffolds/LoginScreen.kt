package com.onetoucheasy.restauranteofertas.ui.scaffolds

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onetoucheasy.restauranteofertas.R
import com.onetoucheasy.restauranteofertas.ui.theme.Black
import com.onetoucheasy.restauranteofertas.ui.theme.Gray
import com.onetoucheasy.restauranteofertas.ui.theme.MainYellow
import com.onetoucheasy.restauranteofertas.ui.theme.Transparent
import com.onetoucheasy.restauranteofertas.ui.viewModels.LoginViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.onetoucheasy.restauranteofertas.ui.theme.White
import com.onetoucheasy.restauranteofertas.ui.viewModels.LoginState

@Composable
fun LoginScreen(viewModel: LoginViewModel, onRegisterClicked:() -> (Unit) , onLoginFinished:() -> (Unit)) {

    val loginStatus by viewModel.loginState.observeAsState()

    LaunchedEffect(loginStatus){
        if(loginStatus == LoginState.SUCCESS){
            onLoginFinished()
        }
    }

    loginStatus?.let {
        LoginScreenContent(it,onRegisterClicked) { user, password ->
        viewModel.performLogin(user, password)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(loginState: LoginState, onRegisterClicked: () -> (Unit),onLoginClicked: (String, String) -> (Unit)) {
    val width = LocalConfiguration.current.screenWidthDp.dp

    var isCompany by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }

    var emailValid by remember {
        mutableStateOf(false)
    }

    var password by remember {
        mutableStateOf("")
    }
    var passwordValid by remember {
        mutableStateOf(false)
    }

    var invalidCredentials by remember{
        mutableStateOf(false)
    }

    Scaffold(
       modifier = Modifier
   ) {
       Box(modifier = Modifier.fillMaxSize()) {
           Image(painter = painterResource(R.mipmap.login_background),
               contentDescription = stringResource(id = R.string.login_background_image_description),
                modifier = Modifier.fillMaxSize(),
               contentScale = ContentScale.FillBounds
               )
           Text(
               text = stringResource(id = R.string.login_access),
               modifier = Modifier
                   .width(230.dp)
                   .padding(start = 70.dp, top = 70.dp),
               maxLines = 2,
               style = MaterialTheme.typography.headlineLarge
           )

           Column(modifier = Modifier.align(Alignment.Center)) {
               Text(text = stringResource(id = R.string.login_authentication_error),
               modifier = Modifier
                   .width(width * 4.0f / 5)
                   .padding(5.dp)
                   .alpha(
                       if (loginState == LoginState.FAILURE) {
                           1.0f
                       } else {
                           0.0f
                       }
                   ),
                   color = Red
                   )
               FormField(text = email, leadingIcon = Icons.Default.Email, onValueChange = {
                   email = it
                   invalidCredentials = false
                   emailValid = it.contains("@")
                   //emailValid = true
               },
                   screenWidth = width,
                   isInvalidCredential = invalidCredentials
               ){
                   FormLabel(hint = stringResource(id = R.string.form_email_hint))
               }

               FormField(text = password, leadingIcon = Icons.Default.Lock, trailingIcon = Icons.Default.Warning, isPassword = true, onValueChange = {
                   password = it
                   invalidCredentials = false
                   //  passwordValid = it.length > 8 && it.contains()
                   passwordValid = it.length > 8 //TODO Add RegEx?
                   //passwordValid = true
               }, screenWidth = width,
                   isInvalidCredential = invalidCredentials
               ){
                   FormLabel(hint = stringResource(id = R.string.form_password_hint))

               }
               Button(onClick = {
                   if(!emailValid || !passwordValid){
                       invalidCredentials = true
                   }
                   Log.d("Token", "Login access started")
                   Log.d("Token", "$invalidCredentials")
                   if(!invalidCredentials){
                       Log.d("Token", "Login on click")
                       onLoginClicked(email,password)
                   }

                   },
                   modifier = Modifier
                       .width(width * 3.0f / 4)
                       .align(CenterHorizontally)
                       .padding(top = 50.dp), colors = ButtonDefaults.buttonColors(MainYellow, Black, Gray, Black)) {
                    Text(stringResource(id = R.string.login_login_button))
               }

               Button(onClick = { onRegisterClicked() },
                   modifier = Modifier
                       .width(width * 2.0f / 3)
                       .align(CenterHorizontally),
                   colors = ButtonDefaults.buttonColors(Transparent,Black, Gray,Black)) {
                   Text(stringResource(id = R.string.login_no_account_customer))
               }
           }
       }
   }
}

@Preview
@Composable
fun LoginScreen_Preview() {
    LoginScreenContent( LoginState.NONE,{}) { _, _ ->}
}


//-----Form Field


@Composable
fun FormField(text: String,
              leadingIcon: ImageVector,
              trailingIcon:ImageVector? = null,
              screenWidth: Dp,
              isPassword: Boolean = false,
              isInvalidCredential: Boolean,
              visible: Boolean = true,
              onValueChange: (String) -> (Unit),
              label: (@Composable () -> (Unit))? = null) {

    FormFieldContent(text, leadingIcon, trailingIcon,screenWidth,isPassword,isInvalidCredential,visible,onValueChange,label)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormFieldContent(text: String,
                     leadingIcon: ImageVector,
                     trailingIcon:ImageVector? = null,
                     screenWidth: Dp,
                     isPassword: Boolean = false,
                     isInvalidCredential: Boolean,
                     visible: Boolean = true,
                     onValueChange: (String) -> (Unit),
                     label: (@Composable () -> (Unit))? = null)
{
    TextField(value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(screenWidth * 4.0f / 5)
            .background(White)
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(
                    1.dp, if (!visible) {
                        Transparent
                    } else {
                        if (isInvalidCredential) {
                            Red
                        } else {
                            Black
                        }
                    }
                )
                ,RoundedCornerShape(8.dp)
            )
            .alpha(
                if (visible) {
                    1.0f
                } else {
                    0.0f
                }
            ),
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = leadingIcon.name)
        },
        trailingIcon = {
            trailingIcon?.let {
                Icon(imageVector = it, contentDescription  = trailingIcon.name)
            }
        },
        textStyle = if(isInvalidCredential){
            LocalTextStyle.current.copy(Red)}else{
            LocalTextStyle.current},
        placeholder = {
            if (label != null){
                label()
            }
        },
        isError = isInvalidCredential, //TODO Fix, isError only change some elements colors (ex. it does not change the text or border color)
        visualTransformation = if (isPassword){PasswordVisualTransformation()}else{VisualTransformation.None},
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
    )
}

@Preview
@Composable
fun FormField_Preview() {
    FormFieldContent("", Icons.Default.Lock, Icons.Default.Warning, screenWidth = 500.dp ,true , false, true, {_->Unit}, {
        FormLabel(
        hint = "Password", TextDecoration.None
    )
    })
}

//----- FormLabel
@Composable
fun FormLabel(hint: String, textDecoration: TextDecoration = TextDecoration.None){
    Text(text = hint, textDecoration = textDecoration)
}
