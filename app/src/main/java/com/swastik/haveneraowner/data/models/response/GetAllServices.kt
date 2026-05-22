package com.swastik.haveneraowner.data.models.response

data class GetAllServices(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)

data class Result(
    val description: String,
    val id: Int,
    val name: String,
    val owner: Owner,
    val price: String
)

data class Owner(
    val account_holder_name: String,
    val account_number: String,
    val address_line1: String,
    val address_line2: String,
    val bank_name: String,
    val branch_name: String,
    val city: String,
    val country: String,
    val created_at: String,
    val date_of_birth: String,
    val email: String,
    val emergency_contact: String,
    val first_name: String,
    val id: Int,
    val id_proof_image: String,
    val id_proof_number: String,
    val id_proof_type: String,
    val ifsc_code: String,
    val last_name: String,
    val notes: String,
    val owner: Int,
    val owner_email: String,
    val owner_username: String,
    val phone: String,
    val postal_code: String,
    val profile_image: String,
    val state: String
)