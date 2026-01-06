package com.liftechnology.planalimenticio.main.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.liftechnology.planalimenticio.main.theme.colorWhite


/**
 * A simple generic outlined text field.
 *
 * @param onBoxChanged A lambda to be invoked when the text field value changes.
 */
@Composable
fun TextFieldGeneric(
    regex: Regex,
    onBoxChanged: (String) -> Unit,
) {

    var changeText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = changeText,
        onValueChange = { newValue: String ->
            if (newValue.isEmpty() || regex.matches(newValue)) {
                changeText = newValue
                onBoxChanged(newValue)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorWhite,
            unfocusedContainerColor = colorWhite
        )
    )
}