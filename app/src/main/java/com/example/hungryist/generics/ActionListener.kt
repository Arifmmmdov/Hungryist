package com.example.hungryist.generics

interface ActionListener<Item> {
    fun run(item: Item)
}