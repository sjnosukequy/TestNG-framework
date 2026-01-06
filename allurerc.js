export default {
    name: "Allure Report",
    output: "./reports/allure",
    historyPath: undefined,
    plugins: {
        awesome: {
            enabled: true,
            options: {
                singleFile: false,
                reportLanguage: "en",
            },
        },
    },
    variables: {
    },
    environments: {
    },
};