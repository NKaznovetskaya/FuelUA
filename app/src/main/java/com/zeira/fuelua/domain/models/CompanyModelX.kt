package com.zeira.fuelua.domain.models

data class CompanyModelX(
    val description: String,
    var id: Int,
    val logo: String,
    val name: String,
    val owner: String,
    val phone: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompanyModelX

        if (description != other.description) return false
        if (id != other.id) return false
        if (logo != other.logo) return false
        if (name != other.name) return false
        if (owner != other.owner) return false
        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + id
        result = 31 * result + logo.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + phone.hashCode()
        return result
    }
}
