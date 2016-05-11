if [ "$1" == '' ]; then
	echo "update_version x.y.z"
	exit 1;
fi

mvn -f pom.xml versions:set -DnewVersion=$1