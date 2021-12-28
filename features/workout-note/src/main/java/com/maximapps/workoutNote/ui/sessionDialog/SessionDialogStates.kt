package com.maximapps.workoutNote.ui.sessionDialog

import com.maximapps.workoutNote.domain.validators.models.ValidationResult

open class SessionDialogState {
    object Empty: SessionDialogState()
}

sealed class SessionDialogFormStates: SessionDialogState() {
    object ValidUserInput: SessionDialogFormStates()
    data class InvalidInput(val errors: List<ValidationResult>): SessionDialogFormStates()
}
