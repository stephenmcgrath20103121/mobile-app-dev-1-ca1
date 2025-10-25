package ie.setu.assignment1.models

interface StoreCollection {
    fun findAll(): List<StoreModel>
    fun create(store: StoreModel)
    fun update(store: StoreModel)
    fun delete(store: StoreModel)
}