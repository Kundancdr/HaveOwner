package com.swastik.haveneraowner.data.models.request

data class UpdateProfileRequest(

    val first_name: String,
    val last_name: String,
    val date_of_birth: String,
    val phone: String,
    val address_line1: String,
    val address_line2: String,
    val city: String,
    val state: String,
    val postal_code: String,
    val country: String,
    val bank_name: String,
    val account_holder_name: String,
    val account_number: String,
    val ifsc_code: String,
    val branch_name: String,
    val id_proof_type: String,
    val id_proof_number: String,
    val emergency_contact: String,
    val notes: String,
    val profile_image: String,
    val id_proof_image: String
)