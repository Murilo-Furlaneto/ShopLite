package br.com.furlaneto.murilo.shoplite.route

sealed class AppPages(val route: String) {
   data object Home : AppPages(route = "home")
    data object Cart : AppPages(route = "cart")
    data object Profile : AppPages(route = "profile")
}