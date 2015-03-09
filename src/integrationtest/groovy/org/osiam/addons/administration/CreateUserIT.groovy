package org.osiam.addons.administration

import org.osiam.addons.administration.pages.Login
import org.osiam.addons.administration.pages.Start

class CreateUserIT extends IntegrationTest {

    def 'create a user'() {
        when:
        via Start
        at Login

        username.value("marissa")
        password.value("koala")
        loginButton.click()

        $("a", href: "create").click()

        $("#username").value("testuser")
        $("#password").value("testuser")
        $("#btnCreate").click()

        then:
        title == "Edit user"
        assert $("#userName").value() == "testuser"
    }
}
