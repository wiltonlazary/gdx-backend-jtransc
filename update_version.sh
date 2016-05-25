if [ "$1" == '' ]; then
	echo "update_version x.y.z"
	exit 1;
fi

sed -i -e "s/jtranscVersion=\(.*\)/jtranscVersion=$1/g" cuboc-demo/gradle.properties
mvn -f pom.xml versions:set -DnewVersion=$1