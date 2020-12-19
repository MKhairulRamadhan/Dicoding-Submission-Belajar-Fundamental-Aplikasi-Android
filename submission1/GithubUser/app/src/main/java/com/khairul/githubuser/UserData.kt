package com.khairul.githubuser

object UserData {

    private val name = arrayOf(
        "Intan Sari",
        "Angle Nura",
        "Susila Nip",
        "Putri ananda",
        "Agnez J",
        "Masyittah Azmi",
        "Rizkina Zuriani",
        "Amalia",
        "Fikri Aula",
        "Runa Putri",
        "Siti Azura"
    )

    private val userName = arrayOf(
        "Intan_Sari",
        "Angle_Nura",
        "Susi_Susi",
        "nanda_ni",
        "Agnez_J",
        "Syitah_o",
        "Rizki_ZN",
        "Amalia",
        "Fikri_Aula",
        "Lamkaruna_putri",
        "Azure_eka"
    )

    private val userRepository = arrayOf(
        "12",
        "1",
        "2",
        "9",
        "3",
        "5",
        "12",
        "1",
        "2",
        "9",
        "3"
    )

    private val userFollower = arrayOf(
        "120",
        "15",
        "84",
        "91",
        "32",
        "55",
        "62",
        "1",
        "32",
        "6",
        "20"
    )

    private val userFollowing = arrayOf(
        "112",
        "21",
        "12",
        "44",
        "33",
        "52",
        "15",
        "12",
        "2",
        "93",
        "31"
    )

    private val userLocation = arrayOf(
        "Banda aceh, NAD",
        "Pidie, NAD",
        "Idi Rayuek, Aceh Timur",
        "Lhokseumawe, Aceh Barat",
        "Medan, Sumatra Utara",
        "Tapak Tuan, Aceh Selatan",
        "Kota Langsa",
        "Kuala Simpang",
        "Lhokseumawe, Aceh Barat",
        "Medan, Sumatra Utara",
        "Tapak Tuan, Aceh Selatan"
    )

    private val userBio = arrayOf(
        "someone who is passionate about learning new things, working hard and able to work in teams, is very interested in data science, software engineering, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in management, statistik, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in math, phisic, and biology.",
        "someone who is passionate about learning new things, working hard and able to work in teams, is very interested in data science, software engineering, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in management, statistik, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in math, phisic, and biology.",
        "someone who is passionate about learning new things, working hard and able to work in teams, is very interested in data science, software engineering, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in management, statistik, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in math, phisic, and biology.",
        "someone who is passionate about learning new things, working hard and able to work in teams, is very interested in data science, software engineering, and artificial intelligence.",
        "someone who work hard and able to work in teams, is very interested in management, statistik, and artificial intelligence.",
    )

    private val userAvatar = intArrayOf(
        R.drawable.b1,
        R.drawable.b2,
        R.drawable.b3,
        R.drawable.b4,
        R.drawable.b5,
        R.drawable.b6,
        R.drawable.b7,
        R.drawable.b8,
        R.drawable.b9,
        R.drawable.b10,
        R.drawable.b11
    )

    val listData: ArrayList<User>
        get() {
            val list = arrayListOf<User>()
            for (position in name.indices) {
                val hero = User()
                hero.name = name[position]
                hero.username = userName[position]
                hero.follower = userFollower[position]
                hero.following = userFollowing[position]
                hero.location = userLocation[position]
                hero.repository = userRepository[position]
                hero.bio = userBio[position]
                hero.avatar = userAvatar[position]
                list.add(hero)
            }
            return list
        }
}