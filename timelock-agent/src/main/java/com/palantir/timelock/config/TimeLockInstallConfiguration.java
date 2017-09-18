/*
 * Copyright 2017 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.timelock.config;

import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.palantir.atlasdb.spi.KeyValueServiceConfig;
import com.palantir.atlasdb.timelock.config.AsyncLockConfiguration;
import com.palantir.atlasdb.timelock.config.ImmutableAsyncLockConfiguration;

/**
 * Static (not live-reloaded) portions of TimeLock's configuration.
 */
@JsonDeserialize(as = ImmutableTimeLockInstallConfiguration.class)
@JsonSerialize(as = ImmutableTimeLockInstallConfiguration.class)
@Value.Immutable
public interface TimeLockInstallConfiguration {
    Optional<PaxosInstallConfiguration> optionalPaxosConfig();

    Optional<KeyValueServiceConfig> optionalKvsConfig();

    ClusterConfiguration cluster();

    @Value.Default
    default AsyncLockConfiguration asyncLock() {
        return ImmutableAsyncLockConfiguration.builder().build();
    }

    @Value.Default
    default PaxosInstallConfiguration paxos() {
        return optionalPaxosConfig().orElseGet(() -> ImmutablePaxosInstallConfiguration.builder().build());
    }

    @Value.Check
    default void check() {
        Preconditions.checkArgument(optionalPaxosConfig().isPresent() || optionalKvsConfig().isPresent(),
                "Exactly one of PaxosInstallConfiguration or KeyValueServiceConfig"
                        + " must be present in the timelock install config.");
    }

}
