package com.swastik.haveneraowner.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swastik.haveneraowner.ui.theme.GraySurface
import com.swastik.haveneraowner.ui.theme.Purple40
import com.swastik.haveneraowner.ui.theme.TextPrimary
import com.swastik.haveneraowner.ui.theme.TextSecondary

@Composable
fun DesignerTextFieldAddRoom(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int? = null,       // 🔑 optional char limit
    maxWords: Int? = null,        // 🔑 optional word limit
    numericOnly: Boolean = false, // 🔑 only digits (for phone/room number)
    onDone: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            var filtered = input

            // ✅ Digits only
            if (numericOnly) {
                filtered = filtered.filter { it.isDigit() }
            }

            // ✅ Max length check
            if (maxLength != null && filtered.length > maxLength) {
                filtered = filtered.take(maxLength)
            }

            // ✅ Max words check
            if (maxWords != null) {
                val words = filtered.trim().split("\\s+".toRegex())
                if (words.size > maxWords) {
                    filtered = words.take(maxWords).joinToString(" ")
                }
            }

            onValueChange(filtered)
        },
        label = {
            Text(
                text = label,
                fontSize = 13.sp,
                color = TextSecondary
            )
        },
        minLines = minLines,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone?.invoke()
                focusManager.clearFocus()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            focusedContainerColor = GraySurface,
            unfocusedContainerColor = GraySurface,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = Purple40,
            unfocusedBorderColor = TextSecondary,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}
